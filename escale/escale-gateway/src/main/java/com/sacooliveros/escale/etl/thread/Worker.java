package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.message.Mensaje;
import com.sacooliveros.escale.log.Logp;
import com.sacooliveros.escale.service.EscaleService;
import com.sacooliveros.escale.service.exception.EscaleServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ricardo on 19/06/2016.
 */
public class Worker implements Runnable {
    public static final Logger LOG = LoggerFactory.getLogger(Worker.class);

    private boolean reading;
    private EscaleService escaleService;
    private Filter workerFilter;
    private BlockingQueue<Mensaje> cola;


    public Worker(EscaleService escaleService, ServerConfiguration config, BlockingQueue<Mensaje> cola) {
        this.escaleService = escaleService;
        this.cola = cola;
        this.reading = Boolean.TRUE;
        this.createFilter(config);
    }


    private void createFilter(ServerConfiguration config) {
        workerFilter = new Filter();
        workerFilter.setExpandLevel(config.getExpandLevel());
    }

    @Override
    public void run() {

        while (reading) {
            try {

                Mensaje mensaje = obtenerMensaje();

                procesarColegio(mensaje);

                procesarDetallesPorAnio(mensaje);

                Logp.showTrx(mensaje.getId(), "PROCESAR", mensaje.getInit());
            } catch (Exception e) {
                LOG.error("Error del Sistema", e);
            }
        }
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
        } catch (EscaleServiceException e) {
            LOG.warn("No se proceso el detalle del Colegio [" + colegio.getCodigo() + "] en el anio [" + filter.getYear() + "]", e);
        }
    }

    /**
     * Lee el mensaje de cola de comunicacion cada 1 segundo
     *
     * @return Mensaje
     */
    public Mensaje obtenerMensaje() {
        Mensaje mensaje;
        LOG.trace("Esperando leer de la cola ...");
        while (true) {
            try {
                LOG.trace("Verificando mensaje de la cola");
                mensaje = cola.poll(1, TimeUnit.SECONDS);
                if (mensaje != null) {
                    Logp.showTrx(mensaje.getId(), "EN_COLA", mensaje.getInit());
                    break;
                }
            } catch (InterruptedException e) {
                LOG.warn("No se pudo leer de la cola");
            }
        }

        LOG.debug("Mensaje obtenido de la cola [" + mensaje + "]s");

        return mensaje;
    }
}
