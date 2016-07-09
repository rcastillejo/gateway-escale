package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.message.Mensaje;
import com.sacooliveros.escale.etl.util.Identificador;
import com.sacooliveros.escale.service.EscaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Ricardo on 18/06/2016.
 */
public class Reader implements Runnable {
    public static final Logger LOG = LoggerFactory.getLogger(Reader.class);
    private boolean reading;
    private Identificador identificador;
    private EscaleService escaleService;
    private String[] years;
    private BlockingQueue<Mensaje> cola;
    private Filter readerFilter;
    private AtomicBoolean workerEnable;

    public Reader(Identificador identificador, EscaleService escaleService, ServerConfiguration config, BlockingQueue<Mensaje> cola,
                  AtomicBoolean workerEnable) {
        this.identificador = identificador;
        this.escaleService = escaleService;
        this.years = config.getYears();
        this.cola = cola;
        this.reading = Boolean.TRUE;
        this.createFilter(config);
        this.workerEnable = workerEnable;
    }

    private void createFilter(ServerConfiguration config) {
        readerFilter = new Filter();
        readerFilter.addLevels(config.getLevels());
        readerFilter.addStates(config.getStates());
    }


    @Override
    public void run() {

        leerColegios();

        verificarWorkers();
    }

    private void verificarWorkers(){
        while(!cola.isEmpty()){
            try {
                LOG.info("Colegios restantes por procesar [{}] ...", cola.size());
                Thread.sleep(1000L);
            }catch (InterruptedException e){
                LOG.warn("La espera fue interrumpida 1s");
            }
        }
        LOG.info("Colegios leidos completamente");
        workerEnable.set(Boolean.FALSE);
    }

    private void leerColegios(){
        try {
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
        } catch (Exception e) {
            LOG.error("Error del Sistema", e);
        }
    }

    private void almacenarMensajes(List<Institucion> colegios) {
        for (Institucion colegio : colegios) {
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


}
