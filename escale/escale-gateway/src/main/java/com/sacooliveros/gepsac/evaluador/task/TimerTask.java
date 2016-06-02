/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.gepsac.evaluador.task;

import com.sacooliveros.gepsac.evaluador.config.Configuration;
import com.sacooliveros.gepsac.evaluador.message.Mensaje;
import com.sacooliveros.gepsac.evaluador.util.Identificador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * @author Ricardo
 */
public class TimerTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(TimerTask.class);
    protected Identificador idf;
    protected BlockingQueue<Mensaje> colaEvaluacion;
    //protected Experto experto;
    protected long waitMilis;


    public void configure(Configuration configuration, BlockingQueue<Mensaje> colaEvaluacion) {
        this.idf = Identificador.getInstance(configuration.getBrokerName());
        this.colaEvaluacion = colaEvaluacion;
        //this.experto = new ExpertoService();
        this.waitMilis = configuration.getTimeForThreads();
    }

    @Override
    public void run() {

        while (Boolean.TRUE) {

            log.info("Inicia la evaluación de las respuestas de la evaluación de acoso escolar");

            try {

                /**
                 * 4.1.2.	El sistema busca los registros de las evaluaciones en
                 * estado “Registrado”.

                log.info("El sistema busca los registros de las evaliaciones en estado 'Registrado'");
                List<EvaluacionAcosoEscolar> evaluaciones = buscaEvaluacionesAcosoEscolar();
                 */
                /**
                 * Se crea el mensaje para cada evaluacion

                for (EvaluacionAcosoEscolar evaluacion : evaluaciones) {
                    enviarMensaje(evaluacion);
                }

            } catch (ExpertoServiceException e) {
                log.error(e.getMessage(), e);*/
            } catch (Exception e) {
                log.error("Error desconocido en el sistema", e);
            } finally {
                try {
                    log.debug("Esperando a consultar [" + waitMilis + "] ...");
                    Thread.sleep(waitMilis);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Busca los registros de las evaluaciones en estado Registrado
     *
     * @return Evaluaciones en estado Registrado

    private List<EvaluacionAcosoEscolar> buscaEvaluacionesAcosoEscolar() {
        return experto.listarEvaluacionAcosoEscolar(Estado.EvaluacionAcosoEscolar.RESUELTO);
    }

    private void enviarMensaje(EvaluacionAcosoEscolar evaluacion) {
        Mensaje evaluacionModel = new Mensaje();
        evaluacionModel.setId(idf.getCode());
        evaluacionModel.setRequest(evaluacion);

        boolean insertaOk = colaEvaluacion.offer(evaluacionModel);
        if (insertaOk) {
            log.debug("Se ingreso la evaluacion a revisar [{}]", evaluacion.getCodigo());
        } else {
            log.debug("No Se ingreso las evaluaciones para la revision [{}]", evaluacion.getCodigo());
        }
    }*/
}
