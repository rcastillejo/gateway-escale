/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ofernandez
 */
public class Logp {

    private static final Logger log = LoggerFactory.getLogger(Logp.class);
    private static final String SIMPLE_FORMAT = "{}|{}|{}|{}";
    private static final String TRX_FORMAT = "{}|{}|{}|{}|{}";

    /**
     * Muestra en el log de performance una entrada con el formato:
     * &lt;PROCESO&gt;|&lt;INICIO(ms)&gt;|&lt;FIN(ms)&gt;|&lt;TOTAL(ms)&gt;
     * <br/>Al ser invocado el metodo, toma el tiempo en milisegundos el cual sera usado
     * como tiempo final. Junto con el tiempo inicial recibido se obtendra el tiempo total
     * @param process Es el nombre del proceso en medicion
     * @param init Es el tiempo inicial en milisegundos
     */
    public static void show(String process, long init) {
        long end;
        long total;

        end = System.currentTimeMillis();
        total = end - init;
        log.info(SIMPLE_FORMAT, new Object[]{process, init, end, total});
    }
    
    /**
     * Muestra en el log de performance una entrada con el formato:
     * &lt;TRAX_ID&gt;|&lt;PROCESO&gt;|&lt;INICIO(ms)&gt;|&lt;FIN(ms)&gt;|&lt;TOTAL(ms)&gt;
     * <br/>Al ser invocado el metodo, toma el tiempo en milisegundos el cual sera usado
     * como tiempo final. Junto con el tiempo inicial recibido se obtendra el tiempo total
     * @param trxId Identificador de la transaccion.
     * @param process Es el nombre del proceso en medicion
     * @param init Es el tiempo inicial en milisegundos
     */
    public static void showTrx(String trxId, String process, long init) {
        long end;
        long total;

        end = System.currentTimeMillis();
        total = end - init;
        log.info(TRX_FORMAT, new Object[]{trxId, process, init, end, total});
    }
}
