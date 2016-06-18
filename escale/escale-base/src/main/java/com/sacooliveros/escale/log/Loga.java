/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Este log es usado para enviar Alertas
 * @author ofernandez
 */
public class Loga {
    private static final Logger log = LoggerFactory.getLogger(Loga.class);
    private static final String SIMPLE_FORMAT = "[{}]{}";

    /**
     * Muestra en el log de performance una entrada con el formato:
     * &lt;PROCESO&gt;|&lt;INICIO(ms)&gt;|&lt;FIN(ms)&gt;|&lt;TOTAL(ms)&gt;
     * <br/>Al ser invocado el metodo, toma el tiempo en milisegundos el cual sera usado
     * como tiempo final. Junto con el tiempo inicial recibido se obtendra el tiempo total
     * @param trxId Es el nombre del proceso en medicion
     * @param message Es el mensaje a enviar
     */
    public static void alert(String trxId, String message) {
        log.warn(SIMPLE_FORMAT, trxId, message);
    }
}
