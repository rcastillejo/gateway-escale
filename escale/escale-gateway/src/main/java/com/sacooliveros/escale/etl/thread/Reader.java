package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.message.Mensaje;
import com.sacooliveros.escale.etl.util.Identificador;
import com.sacooliveros.escale.service.EscaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

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

    public Reader(Identificador identificador, EscaleService escaleService, ServerConfiguration config, BlockingQueue<Mensaje> cola) {
        this.identificador = identificador;
        this.escaleService = escaleService;
        this.years = config.getYears();
        this.cola = cola;
        this.reading = Boolean.TRUE;
        this.createFilter(config);
    }

    private void createFilter(ServerConfiguration config) {
        readerFilter = new Filter();
        readerFilter.addLevels(config.getLevels());
        readerFilter.addStates(config.getStates());
    }


    @Override
    public void run() {
        while (reading) {
            try {
                int cantidad = escaleService.calcularTotalColegios(readerFilter);
                LOG.debug("Procesando colegios [{}]", cantidad);
                while (escaleService.existeColegiosPorConsultar()) {
                    try {
                        List<Colegio> colegios = escaleService.consultarSiguienteGrupoColegios(readerFilter);
                        LOG.debug("Colegios consultados [{}]", colegios.size());
                        almacenarMensajes(colegios);
                    } catch (Exception e) {
                        LOG.error("Error al consultar los colegios", e);
                    }
                }
            }catch (Exception e){
                LOG.error("Error del Sistema", e);
            }

        }
    }

    private void almacenarMensajes(List<Colegio> colegios){
        for (Colegio colegio : colegios) {
            almacenarMensaje(colegio);
        }
    }

    private void almacenarMensaje(Colegio colegio) {
        Mensaje mensaje = new Mensaje();
        mensaje.setId(identificador.getCode());
        mensaje.setColegio(colegio);
        mensaje.setYears(years);

        boolean insertaOk = cola.offer(mensaje);
        if (insertaOk) {
            LOG.debug("Se ingreso la evaluacion a revisar [{}]", mensaje);
        } else {
            LOG.debug("No Se ingreso las evaluaciones para la revision [{}]", mensaje);
        }
    }


}
