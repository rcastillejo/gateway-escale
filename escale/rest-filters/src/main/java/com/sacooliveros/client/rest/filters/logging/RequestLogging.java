package com.sacooliveros.client.rest.filters.logging;

import com.sacooliveros.client.rest.filters.util.ByteArrayEntityStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

/**
 * Permite capturar el request y responde de la interface HTTP para registrarlos
 * en logs.
 *
 * @author rcastillejo
 * @since 14/03/2016
 */
public class RequestLogging extends BasicLogging {

    private static final Logger log = LoggerFactory.getLogger(RequestLogging.class);
    private static final String PATTERN = ">>";
    private final ByteArrayEntityStream entityStream;

    public RequestLogging() {
        super(log, PATTERN);
        this.entityStream = new ByteArrayEntityStream();
    }

    protected void printBody(OutputStream entityStream) {
        super.printBody(entityStream.toString());
    }
}
