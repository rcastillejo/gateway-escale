package com.sacooliveros.escale.etl;


import com.sacooliveros.escale.etl.message.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

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
    public static void main(String[] args) throws Exception {
        localServer = new LocalServer("broker.properties", new LinkedBlockingQueue<Mensaje>());

        LOG.info("Iniciando server");
        localServer.start();
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
            LOG.info("Se ha recibido la senial USR2");
            try {
                localServer.stop();
            } catch (Exception e) {
                LOG.error("No se pudo detener el server", e);
            }
            System.exit(0);
        }
    }
}
