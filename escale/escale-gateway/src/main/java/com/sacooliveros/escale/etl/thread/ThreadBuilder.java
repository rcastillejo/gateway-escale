package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.listener.ReaderCompleteListener;
import com.sacooliveros.escale.etl.util.Identificador;
import com.sacooliveros.escale.service.EscaleService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Ricardo on 19/06/2016.
 */
public class ThreadBuilder {
    private static final AtomicBoolean workersEnable = new AtomicBoolean(Boolean.TRUE);

    private ThreadBuilder() {
    }

    public static Reader newInstanceReader(ServerConfiguration configuration, BlockingQueue queue, EscaleService service,
                                           ThreadPoolExecutor threadPool){
        Reader reader = new Reader(
                Identificador.getInstance(configuration.getServerName()),
                service, configuration,
                queue);

        reader.addListener(new ReaderCompleteListener(workersEnable, queue, threadPool, configuration.getTimeForThreads()));

        return reader;
    }

    public static Worker newInstaceWorker(ServerConfiguration configuration, BlockingQueue queue, EscaleService service){
        return new Worker(
                service, configuration,
                queue, workersEnable);
    }
}
