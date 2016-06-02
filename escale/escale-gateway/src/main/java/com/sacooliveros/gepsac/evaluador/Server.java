package com.sacooliveros.gepsac.evaluador;


import com.sacooliveros.gepsac.evaluador.config.Configuration;
import com.sacooliveros.gepsac.evaluador.message.Mensaje;
import com.sacooliveros.gepsac.evaluador.task.EvaluadorTask;
import com.sacooliveros.gepsac.evaluador.task.TimerTask;
import com.sacooliveros.gepsac.evaluador.task.Type;
import com.sacooliveros.gepsac.evaluador.thread.ThreadPoolBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);
    /**
     * Variable indicadora de analisis de threads en caso de shutdown
     */
    private static boolean yaEntroAnalizar = false;

    private Server() {
    }

    /**
     * Metodo estatico para invocar el shutdown del controlador
     *
     * @param id
     * @param numThreads
     * @param timeForThreadsMili
     * @param brokerIntervalMili
     */
    public static void shutdownControlador(String id, int numThreads, int timeForThreadsMili, int brokerIntervalMili) {

        //  Valida si ya entro a analizar alguno de los threads
        boolean analizar = false;

        synchronized (Server.class) {
            if (!yaEntroAnalizar) {
                yaEntroAnalizar = true;
                analizar = true;
            }
        }

        if (analizar) {
            // -------------------------------------------------------------------------------------------------------------- //
            //
            // Mecanismo de verificacion de transacciones en proceso					
            //
            // -------------------------------------------------------------------------------------------------------------- //
            int retry = timeForThreadsMili / brokerIntervalMili;
            int c = 1;
            boolean flagChequeo = true;
            /*while (flagChequeo && retry-- > 0) {
                flagChequeo = false;
                for (int i = 0; i < numThreads; i++) {
                    log.debug("{}\tVerificando estado del hilo-{}: [" + threadData[i].getStatus() + "]", new Object[]{id, i});
                    if (threadData[i].isProcess()) {
                        flagChequeo = true;
                    } else {
                        threadData[i].setStatus(Constants.STATUS_DOWN);
                    }
                }

                if (flagChequeo) {
                    try {
                        log.debug((id, Server.class.getSimpleName(), Constants.METHOD_RUN, "Threads ocupados, esperando 5 segundos para realizar el (" + c + ") reintento.");
                        Thread.sleep(brokerIntervalMili);
                    } catch (Exception e) {
                    }
                }
                c++;
            }*/
        }

    }

    /**
     * Metodo de ejecucion main
     *
     * @param args Array con los parametros de entrada
     */
    public static void main(String[] args) throws Exception {

        //  Se leen las propiedades del Broker (broker.properties) y las propiedades de los endpoints ()
        Configuration configuration = new Configuration("broker.properties");

        // --------------------------------------------------------------------------------- //
        //
        // ASIGNA PARAMETROS
        //
        // --------------------------------------------------------------------------------- //    
        /**
         * Se carga la lista de codigos de Error y sus correspondientes Mensajes
         */
        log.info("Iniciando Servicio Evaluardor [" + configuration.getServerName() + "]");
        log.debug("Parametros: " + configuration);

        // --------------------------------------------------------------------------------- //
        //
        // Inicializando el componente DAO con LA CONEXION BD
        //
        // --------------------------------------------------------------------------------- //
        log.info("Iniciando Configuracion Dao Factory");
        //SingletonDAOFactory.init(SingletonDAOFactory.MY_IBATIS);

        // --------------------------------------------------------------------------------- //
        //
        // INICIANDO HILOS DE ATENCION
        //
        // --------------------------------------------------------------------------------- //
        /*
         * Los procesos syncronicos deben usar uno y solo un Thread para leer de la cola
         * otros Threads deben escribir en la cola a donde se reenvia el mensaje procesado.
         * El Thread que lee y los que escriben deben estar comunicados por una cola Java.
         * deben manejar diferentes instancias de API SIX sino =======> CORE DUMP en la nativa
         * Esto es hasta la version 3.2.1 de SIX... si se tienen revisiones superiores
         * validar ya que puede haber cambiado para mejor 
         */
        log.info("Iniciando Configuracion del Pool de Evaluadores");
        ThreadPoolBuilder.init();

        executeTypes(configuration);

    }

    private static void executeTypes(Configuration configuration) throws Exception {
        for (Type type : configuration.getTypes()) {
            BlockingQueue<Mensaje> colaEvaluaciones = new LinkedBlockingQueue<Mensaje>();

            executeTimer(configuration, colaEvaluaciones, type.getTimer());

            executeTasks(configuration, colaEvaluaciones, type.getEvaluator());
        }
    }

    private static void executeTasks(Configuration config,
                                     BlockingQueue<Mensaje> colaEvaluaciones,
                                     String evaluatorClassname) throws Exception {

        int numThreads = config.getNumThreads();

        ThreadPoolExecutor threadPool = ThreadPoolBuilder.createThreadFactory(config);

        log.debug("Fabrica para procesos asicronos creado:" + threadPool.getCorePoolSize());
        for (int workerId = 0; workerId < numThreads; workerId++) {
            try {
                Class claz = Class.forName(evaluatorClassname);
                EvaluadorTask task = (EvaluadorTask) claz.newInstance();
                task.configure(workerId, colaEvaluaciones);

                threadPool.execute(task);
                log.debug("Proceso Asincrono Worker[" + task + "] iniciado");
            } catch (Exception e) {
                log.error("No se pudo crear proceso Asincrono Worker[" + workerId + "]", e);
                throw e;
            }
        }
    }

    private static void executeTimer(Configuration configuration,
                                     BlockingQueue<Mensaje> colaEvaluaciones,
                                     String timerClassname) throws Exception {
        ThreadGroup threadGroup = new ThreadGroup("Timer");
        Class claz = Class.forName(timerClassname);
        TimerTask task = (TimerTask) claz.newInstance();
        task.configure(configuration, colaEvaluaciones);

        Thread exeTask = new Thread(threadGroup, task);
        exeTask.setName("TimerTask");
        exeTask.start();
    }

    /**
     * Clase para la intercepcion las seniales USR1 y USR2 desde el Sistema
     * Operativo.
     */
    private static class SignalUSR2Handler implements SignalHandler {

        /*
         * Metodo handle
         * @param sig - senial del sistema operativo
         */
        public void handle(Signal sig) {
            log.info("Se ha recibido la senial USR2");

            // Indicamos que deben finalizar todos los hilos todos los hilos
            // que se encunetran en ejecucion.
            //shutdownControlador(null, numThreads, timeForThreadsMili, brokerIntervalMili);
            log.info("Se finaliza la maquina virtual...");
            System.exit(0);
        }
    }
}
