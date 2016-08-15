package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.listener.ThreadCompleteListener;
import com.sacooliveros.escale.etl.message.Mensaje;
import com.sacooliveros.escale.etl.util.Identificador;
import com.sacooliveros.escale.service.EscaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ricardo on 18/06/2016.
 */
public class Reader implements Runnable {
    public static final Logger LOG = LoggerFactory.getLogger(Reader.class);
    private Identificador identificador;
    private EscaleService escaleService;
    private String[] years;
    private BlockingQueue<Mensaje> cola;
    private Filter readerFilter;
    private AtomicInteger currentTransactions;
    private int numThreads;
    private Object tokenSynchro;
    private final Set<ThreadCompleteListener> listeners;

    public Reader(Identificador identificador, EscaleService escaleService, ServerConfiguration config, BlockingQueue<Mensaje> cola) {
        this.identificador = identificador;
        this.escaleService = escaleService;
        this.years = config.getYears();
        this.cola = cola;
        this.createFilter(config);
        this.numThreads = config.getNumThreads();
        this.listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();
    }

    private void createFilter(ServerConfiguration config) {
        readerFilter = new Filter();
        readerFilter.addLevels(config.getLevels());
        readerFilter.addStates(config.getStates());
    }
    @Override
    public void run() {
        LOG.info("Reader iniciando ...");
        try {
            leerColegios();

        } catch (Exception e) {
            LOG.error("Error del Sistema", e);
        } finally {
            notifyListeners();
        }
        LOG.info("Reader detenido");
    }

    private void leerColegios(){
        int cantidad = escaleService.calcularTotalColegios(readerFilter);
        LOG.info("Procesando colegios [{}] ...", cantidad);
        while (escaleService.existeColegiosPorConsultar()) {
            try {
                List<Institucion> colegios = escaleService.consultarSiguienteGrupoColegios(readerFilter);
                LOG.debug("Colegios procesando [leidos={},penientes={}]", new Object[]{escaleService.leidos(), escaleService.pendientes()});
                almacenarMensajes(colegios);
            } catch (Exception e) {
                LOG.error("Error al consultar los colegios", e);
            }
        }
        LOG.info("Colegios leidos completamente");
    }

    private void almacenarMensajes(List<Institucion> colegios) {
        for (Institucion colegio : colegios) {
            verificarYAsignarWorkers();
            almacenarMensaje(colegio);
        }
    }

    private void almacenarMensaje(Institucion colegio) {
        Mensaje mensaje = new Mensaje();
        mensaje.setId(identificador.getCode());
        mensaje.setColegio(colegio);
        mensaje.setYears(years);

        boolean insertaOk = cola.offer(mensaje);
        if (insertaOk) {
            LOG.trace("Se ingreso el colegio para su procesamiento [{}]", mensaje);
        } else {
            LOG.warn("No Se ingreso las colegio para la revision [{}]", mensaje);
        }
    }


    private void verificarYAsignarWorkers(){
        verificarDisponibilidadWorkers();
        asignarWorker();
    }

    /*
     * El siguiente WHILE es para imedir que se sigan leyendo transacciones cuando no hay hilos de atencion disponibles
     * Los workers deben reducir el contador que se incrementa
     */
    private void verificarDisponibilidadWorkers(){
        while (true) {
            synchronized (tokenSynchro) {
                if (currentTransactions.intValue() >= numThreads) {
                    try {
                        LOG.warn("NO SE ACEPTAN transacciones, no hay capacidad de coperacion disponible. [{}] transacciones en curso.", new Object[]{currentTransactions.intValue()});
                        tokenSynchro.wait(1000);
                    } catch (InterruptedException e) {
                        continue;
                    }
                } else {
                    break;
                }
            }
        }
    }

    private void asignarWorker(){
        synchronized (tokenSynchro) {
            int txnEnCurso = currentTransactions.incrementAndGet();
            LOG.debug("Aceptamos transaccion. [{}] transacciones en curso.", new Object[]{txnEnCurso});
        }
    }


    public void setTokenSynchro(Object tokenSynchro) {
        this.tokenSynchro = tokenSynchro;
    }

    public void setCurrentTransactions(AtomicInteger currentTransactions) {
        this.currentTransactions = currentTransactions;
    }

    public final void addListener(final ThreadCompleteListener listener){
        listeners.add(listener);
    }

    private final void notifyListeners() {
        for (ThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this);
        }
    }
}
