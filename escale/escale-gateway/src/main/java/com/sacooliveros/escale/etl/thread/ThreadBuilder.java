package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.etl.config.ServerConfiguration;
import com.sacooliveros.escale.etl.util.Identificador;
import com.sacooliveros.escale.service.EscaleService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Ricardo on 19/06/2016.
 */
public class ThreadBuilder {

    private ThreadBuilder() {
    }

    public static Runnable newInstanceReader(ServerConfiguration configuration, BlockingQueue queue, EscaleService service){
        return new Reader(
                Identificador.getInstance(configuration.getServerName()),
                service, configuration,
                queue);
    }

    public static Runnable newInstaceWorker(ServerConfiguration configuration, BlockingQueue queue, EscaleService service){
        return new Worker(
                service, configuration,
                queue);
    }
}
