package com.sacooliveros.escale.etl;


import com.sacooliveros.escale.etl.message.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private static LocalServer localServer;

    private Server() {
    }

    /**
     * Metodo de ejecucion main
     *
     * @param args Array con los parametros de entrada
     */
    public static void main(String[] args) {
        start(args);
    }

    public static void start(String[] args) {
        System.out.println("Iniciando ["+ Arrays.toString(args) +"] ...");
        localServer = new LocalServer("broker.properties", new LinkedBlockingQueue<Mensaje>());
        try {
            localServer.start();
            LOG.info("Servicio inicado");
        } catch (Exception e) {
            LOG.error("Servicio no se pudo iniciar", e);
        }
    }

    public static void stop(String[] args) {
        System.out.println("Deteniendo ["+Arrays.toString(args)+"] ...");
        if (localServer != null) {
            try {
                localServer.stop();
                LOG.info("Servicio detenido");
            } catch (Exception e) {
                LOG.error("Servicio no se pudo detener", e);
            }
        } else {
            LOG.warn("Servicio no iniciado");
        }
    }
}
