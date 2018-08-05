package com.sacooliveros.client.rest.filters.logging;

import com.sacooliveros.client.rest.filters.util.ByteArrayEntityStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Permite capturar el request y responde de la interface HTTP para registrarlos
 * en logs.
 *
 * @author rcastillejo
 * @since 14/03/2016
 */
public class ResponseLogging extends BasicLogging {

    private static final Logger log = LoggerFactory.getLogger(ResponseLogging.class);
    private static final String PATTERN = "<<";
    private final ByteArrayEntityStream entityStream;

    public ResponseLogging(String trace) {
        super(log, PATTERN);
        this.entityStream = new ByteArrayEntityStream();
        setTrace(trace);
    }

    protected void printBody(InputStream entityStream) {
        try {
            byte[] responseBuffer = this.entityStream.copySafeResponseEntityStream(entityStream);
            super.printBody(new String(responseBuffer));
        } catch (IOException e) {
            super.printBody(entityStream.toString());
        }
    }


    protected InputStream getEntityStream() {
        return entityStream.getByteArrayInputStream();
    }
}
