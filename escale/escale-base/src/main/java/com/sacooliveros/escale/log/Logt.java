/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rcastillejo
 */
public class Logt {

    private static final Logger LOG = LoggerFactory.getLogger(Logt.class);

    public static void show(String message) {
        LOG.info(message);
    }

    public static void show(String id, String message) {
        LOG.info("id='{}',{}", id, message);
    }

    public static void show(String id, String operacion, String message) {
        if (id != null) {
            LOG.info("id='{}',operacion='{}', {}",
                    new Object[]{id, operacion, message});
        }
    }
}
