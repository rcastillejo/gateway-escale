package com.sacooliveros.client.rest.filters.util;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author rcastillejo
 */
public class ByteArrayEntityStream {
    private OutputStream entityOutputStream;
    private InputStream entityInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ByteArrayInputStream byteArrayInputStream;

    public ByteArrayEntityStream() {
    }

    public byte[] copySafeResponseEntityStream(InputStream entityStream) throws IOException {
        this.entityInputStream = entityStream;

        byteArrayOutputStream = new ByteArrayOutputStream();

        IOUtils.copy(entityStream, byteArrayOutputStream);

        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        return byteArrayOutputStream.toByteArray();
    }


    public ByteArrayInputStream getByteArrayInputStream() {
        return byteArrayInputStream;
    }

}
