package com.sacooliveros.escale.etl;


import com.sacooliveros.escale.client.rest.RestEscaleClientService;
import com.sacooliveros.escale.dao.mybatis.MyBatisDAOFactory;
import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.message.Mensaje;
import com.sacooliveros.escale.etl.thread.Reader;
import com.sacooliveros.escale.etl.thread.Worker;
import com.sacooliveros.escale.etl.thread.WorkerThreadPoolBuilder;
import com.sacooliveros.escale.etl.util.Identificador;
import com.sacooliveros.escale.etl.util.ResourceHelper;
import com.sacooliveros.escale.mapper.EscaleMapper;
import com.sacooliveros.escale.service.EscaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private Server() {
    }

    /**
     * Metodo de ejecucion main
     *
     * @param args Array con los parametros de entrada
     */
    public static void main(String[] args) throws Exception {

        Properties config = ResourceHelper.loadConfig("broker.properties");

        //  Se leen las propiedades del Broker (broker.properties) y las propiedades de los endpoints ()
        ServerConfiguration configuration = new ServerConfiguration(config);

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
        MyBatisDAOFactory.init(configuration.getMyBatisResource());

        // --------------------------------------------------------------------------------- //
        //
        // INICIANDO HILOS DE LECTURA
        //
        // --------------------------------------------------------------------------------- //
        BlockingQueue cola = new LinkedBlockingQueue();
        log.info("Iniciando Configuracion del Reader");
        executeTimer(configuration, cola);

        // --------------------------------------------------------------------------------- //
        //
        // INICIANDO HILOS DE ATENCION
        //
        // --------------------------------------------------------------------------------- //
        log.info("Iniciando Configuracion del Pool de Worker");
        executeWorkers(configuration, cola);

    }

    private static void executeWorkers(ServerConfiguration config,
                                       BlockingQueue<Mensaje> colaEvaluaciones) throws Exception {

        int numThreads = config.getNumThreads();

        ThreadPoolExecutor threadPool = WorkerThreadPoolBuilder.createThreadFactory(config);

        log.debug("Fabrica para procesos asicronos creado:" + threadPool.getCorePoolSize());
        for (int workerId = 0; workerId < numThreads; workerId++) {
            try {
                EscaleService service = new EscaleService(
                        RestEscaleClientService.newInstance(config.getClientConfig()),
                        new EscaleMapper(),
                        MyBatisDAOFactory.getColegioDAO(),
                        config.getInstituteBlock());

                Worker worker = new Worker(service, config, colaEvaluaciones);

                threadPool.execute(worker);
                log.debug("Proceso Asincrono Worker[" + worker + "] iniciado");
            } catch (Exception e) {
                log.error("No se pudo crear proceso Asincrono Worker[" + workerId + "]", e);
                throw e;
            }
        }
    }

    private static void executeTimer(ServerConfiguration config,
                                     BlockingQueue<Mensaje> colaEvaluaciones) {
        EscaleService service = new EscaleService(
                RestEscaleClientService.newInstance(config.getClientConfig()),
                new EscaleMapper(),
                MyBatisDAOFactory.getColegioDAO(),
                config.getInstituteBlock());
        Reader reader = new Reader(
                Identificador.getInstance(config.getServerName()),
                service, config,
                colaEvaluaciones);
        ThreadGroup readerGroup = new ThreadGroup("Readers");
        Thread thread = new Thread(readerGroup, reader);
        thread.setName("ReaderTask");
        thread.start();
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
