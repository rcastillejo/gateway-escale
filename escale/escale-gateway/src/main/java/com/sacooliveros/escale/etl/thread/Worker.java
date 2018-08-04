package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.exception.MessageNotFoundException;
import com.sacooliveros.escale.etl.message.Mensaje;
import com.sacooliveros.escale.log.Logp;
import com.sacooliveros.escale.service.EscaleService;
import com.sacooliveros.escale.service.exception.EscaleServiceException;
import com.sacooliveros.escale.service.exception.InstituteDetailNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ricardo on 19/06/2016.
 */
public class Worker implements Runnable {
    public static final Logger LOG = LoggerFactory.getLogger(Worker.class);

    private EscaleService escaleService;
    private Filter workerFilter;
    private BlockingQueue<Mensaje> cola;
    private AtomicBoolean workersEnable;
    private AtomicInteger currentTransactions;
    private Object tokenSynchro;


    public Worker(EscaleService escaleService, ServerConfiguration config, BlockingQueue<Mensaje> cola, AtomicBoolean workersEnable) {
        this.escaleService = escaleService;
        this.cola = cola;
        this.workersEnable = workersEnable;
        this.createFilter(config);
    }


    private void createFilter(ServerConfiguration config) {
        workerFilter = new Filter();
        workerFilter.setExpandLevel(config.getExpandLevel());
    }

    @Override
    public void run() {
        LOG.info("Worker iniciando ...");
        while (workersEnable.get()) {
            Mensaje mensaje = null;
            try {

                mensaje = obtenerMensaje();

                procesarColegio(mensaje);

                procesarDetallesPorAnio(mensaje);

                Logp.showTrx(mensaje.getId(), "PROCESAR", mensaje.getInit());
            } catch (MessageNotFoundException e) {
                LOG.warn("No se encontro más colegios a leer");
            } catch (Exception e) {
                LOG.error("Error del Sistema", e);
            } finally {
                if(mensaje != null){
                    liberarWorker();
                }
            }
        }
        LOG.info("Worker detenido");
    }

    private void procesarColegio(Mensaje mensaje) {
        Institucion colegio = mensaje.getColegio();
        escaleService.transformarGuardarColegio(colegio);
    }

    private void procesarDetallesPorAnio(Mensaje mensaje) {
        Institucion colegio = mensaje.getColegio();
        Filter filter = workerFilter.clone();

        for (String year : mensaje.getYears()) {
            filter.setYear(year);
            procesarDetalle(filter, colegio);
        }
    }

    private void procesarDetalle(Filter filter, Institucion colegio) {
        try {
            List<ColegioDetalle> detalle = escaleService.consultarDetalleColegio(colegio, filter);
            escaleService.guardarDetalleColegio(detalle);
        } catch (InstituteDetailNotFoundException e) {
            LOG.warn("No se encontro el detalle del Colegio [" + e.getCodigoColegio() + "] en el anio [" + filter.getYear() + "]", e);
        } catch (EscaleServiceException e) {
            LOG.error("No se proceso el detalle del Colegio [" + colegio.getCodigo() + "] en el anio [" + filter.getYear() + "]", e);
        }
    }

    /**
     * Lee el mensaje de cola de comunicacion cada 1 segundo
     *
     * @return Mensaje
     */
    private Mensaje obtenerMensaje() {
        Mensaje mensaje = null;
        LOG.trace("Esperando leer de la cola ...");
        while (workersEnable.get()) {
            try {
                LOG.trace("Verificando mensaje de la cola");
                mensaje = cola.poll(1, TimeUnit.SECONDS);
                if (mensaje != null) {
                    Logp.showTrx(mensaje.getId(), "EN_COLA", mensaje.getInit());
                    break;
                }
            } catch (InterruptedException e) {
                LOG.warn("No se pudo leer de la cola", e);
            }
        }

        if (mensaje != null) {

            LOG.debug("Mensaje obtenido de la cola [" + mensaje + "]s");

            return mensaje;
        } else {
            throw new MessageNotFoundException("No se obtuvo colegio a leer");
        }

    }

    /*
     * Reducimos los contadores de transacciones en curso
     */
    private void liberarWorker() {
        synchronized (tokenSynchro) {
            int txnEnCurso = currentTransactions.decrementAndGet();
            if (txnEnCurso < 0) {
                    /*
                     * No deberia bajar menos de CERO, en todo caso, lo dejamos abierto para ver si pasa.
                     */
                LOG.warn("ADVERTENCIA. Transacciones en curso [{}] no deberia ser menor a cero.", new Object[]{txnEnCurso});
            } else {
                tokenSynchro.notify();
                LOG.debug("Liberamos transaccion. [{}] transacciones en curso.", new Object[]{txnEnCurso});
            }
        }
    }


    public void setCurrentTransactions(AtomicInteger currentTransactions) {
        this.currentTransactions = currentTransactions;
    }

    public void setTokenSynchro(Object tokenSynchro) {
        this.tokenSynchro = tokenSynchro;
    }
}
