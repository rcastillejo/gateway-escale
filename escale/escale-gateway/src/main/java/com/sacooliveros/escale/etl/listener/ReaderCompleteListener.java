package com.sacooliveros.escale.etl.listener;

import com.sacooliveros.escale.etl.message.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rcastillejo on 15/08/2016.
 */
public class ReaderCompleteListener implements ThreadCompleteListener{
    public static final Logger LOG = LoggerFactory.getLogger(ReaderCompleteListener.class);
    private final AtomicBoolean workerEnable;
    private final BlockingQueue<Mensaje> cola;
    private final ThreadPoolExecutor threadPool;
    private final int timeToWait;

    public ReaderCompleteListener(AtomicBoolean workerEnable, BlockingQueue<Mensaje> cola, ThreadPoolExecutor threadPool,
                                  int timeToWait) {
        this.workerEnable = workerEnable;
        this.cola = cola;
        this.threadPool = threadPool;
        this.timeToWait = timeToWait;
    }

    @Override
    public void notifyOfThreadComplete(Runnable runnable) {
        try {
            verificarWorkers();
        } catch (Exception e) {
            LOG.error("Error del Sistema", e);
        } finally {
            disableWorkers();
            safelyShutdownThreadPool();
        }
    }

    private void verificarWorkers(){
        while(!isQueueEmpty()){
            try {
                LOG.info("Colegios restantes por procesar [{}] ...", cola.size());
                Thread.sleep(1000L);
            }catch (InterruptedException e){
                LOG.warn("La espera fue interrumpida 1s");
            }
        }
    }

    private void disableWorkers(){
        workerEnable.set(Boolean.FALSE);
        LOG.debug("Workers deshabilitados");
    }

    private void safelyShutdownThreadPool(){
        try {
            threadPool.awaitTermination(timeToWait, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            LOG.warn("La espera de finalizacion de workers fue interrumpida " + timeToWait);
        }
        threadPool.shutdown();
        LOG.debug("Fabrica de Workers Detenido [isTerminating={}, isTerminated={}, isShutdown={}]",
                new Object[]{threadPool.isTerminated(), threadPool.isShutdown(), threadPool.isTerminated()});
    }

    private boolean isQueueEmpty(){
        return cola.isEmpty();
    }
}
