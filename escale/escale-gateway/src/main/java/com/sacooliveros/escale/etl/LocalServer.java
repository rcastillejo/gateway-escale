package com.sacooliveros.escale.etl;

import com.sacooliveros.escale.client.rest.RestEscaleClientService;
import com.sacooliveros.escale.dao.mybatis.MyBatisDAOFactory;
import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.listener.ReaderCompleteListener;
import com.sacooliveros.escale.etl.message.Mensaje;
import com.sacooliveros.escale.etl.thread.Reader;
import com.sacooliveros.escale.etl.thread.ThreadBuilder;
import com.sacooliveros.escale.etl.thread.Worker;
import com.sacooliveros.escale.etl.thread.WorkerThreadPoolBuilder;
import com.sacooliveros.escale.etl.util.ResourceHelper;
import com.sacooliveros.escale.service.EscaleService;
import com.sacooliveros.escale.service.EscaleServiceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ricardo on 19/06/2016.
 */
public class LocalServer {
    private static final Logger LOG = LoggerFactory.getLogger(LocalServer.class);
    private Properties config;
    private ServerConfiguration configuration;
    private MyBatisDAOFactory daoFactory;
    private BlockingQueue<Mensaje> queue;
    private Object tokenSynchro;
    private AtomicInteger currentTransactions;
    private ThreadPoolExecutor threadPool;


    public LocalServer(String resource, BlockingQueue<Mensaje> queue) {
        this.config = ResourceHelper.loadConfig(resource);
        this.configuration = new ServerConfiguration(config);
        LOG.info("Iniciando Configuracion Dao Factory");
        this.daoFactory = new MyBatisDAOFactory(configuration.getMyBatisResource());
        this.queue = queue;
        this.tokenSynchro = new Object();
        this.currentTransactions = new AtomicInteger(0);
        LOG.info("Iniciando Configuracion del Pool de Worker");
        this.threadPool = WorkerThreadPoolBuilder.createThreadFactory(configuration.getNumThreads());
    }

    private void configure(){

    }


    public void startAsTask() throws Exception {
        LOG.info("Iniciando Proceso [" + configuration.getServerName() + "] ...");
        execute();
        LOG.info("Proceso Iniciado [" + configuration.getServerName() + "]");
    }

    public void execute() throws Exception {
        executeReader(configuration, queue);
        executeWorkers(configuration, queue);
    }


    private void executeWorkers(ServerConfiguration config, BlockingQueue<Mensaje> queue) throws Exception {
        LOG.debug("Fabrica para procesos asicronos creado:" + threadPool.getCorePoolSize());
        for (int workerId = 0; workerId < threadPool.getCorePoolSize(); workerId++) {
            try {
                executeWorker(threadPool, config, queue);
            } catch (Exception e) {
                LOG.error("No se pudo crear proceso Asincrono Worker[" + workerId + "]", e);
                throw e;
            }
        }
    }

    private void executeWorker(ThreadPoolExecutor threadPool, ServerConfiguration config, BlockingQueue<Mensaje> colaAtencion) {
        EscaleService service = EscaleServiceBuilder.newInstaceForWorker(
                RestEscaleClientService.newInstance(config.getClientConfig()), config.getConfig(),
                this.daoFactory.getColegioDAO());

        Worker worker = ThreadBuilder.newInstaceWorker(config, colaAtencion, service);
        worker.setTokenSynchro(tokenSynchro);
        worker.setCurrentTransactions(currentTransactions);

        threadPool.execute(worker);
        LOG.debug("Proceso Asincrono Worker[" + worker + "] iniciado");
    }

    private void executeReader(ServerConfiguration config,
                               BlockingQueue<Mensaje> colaAtencion) {

        LOG.info("Iniciando Configuracion del Reader");
        EscaleService service = EscaleServiceBuilder.newInstaceForReader(
                RestEscaleClientService.newInstance(config.getClientConfig()), config.getConfig(),
                this.daoFactory.getColegioDAO(),
                config.getInstituteBlock());

        Reader reader = ThreadBuilder.newInstanceReader(config, colaAtencion, service, threadPool);
        reader.setTokenSynchro(tokenSynchro);
        reader.setCurrentTransactions(currentTransactions);

        ThreadGroup readerGroup = new ThreadGroup("Readers");
        Thread thread = new Thread(readerGroup, reader);
        thread.setName("ReaderTask");
        thread.start();
    }


}
