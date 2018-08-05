package com.sacooliveros.client.rest.filters.logging;


import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by rcastillejo on 11/05/2016.
 */

@Provider
public class LoggingFilter
        implements ClientRequestFilter, ClientResponseFilter {
    public static final String HEADER_TRACE_ID = "trace_id";

    /**
     * {@inheritDoc}
     *
     * @param requestContext {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public void filter(ClientRequestContext requestContext) throws IOException {
        RequestLogging reqLog = new RequestLogging();

        reqLog.registerTrace(requestContext.getEntity());
        requestContext.setProperty(HEADER_TRACE_ID, reqLog.getTrace());

        printRequestHeaders(reqLog, requestContext);
        printRequestBody(reqLog, requestContext);
    }

    protected void printRequestHeaders(RequestLogging reqLog, ClientRequestContext requestContext) {
        reqLog.printHeaders(requestContext.getStringHeaders());
    }

    protected void printRequestBody(RequestLogging reqLog, ClientRequestContext requestContext) {
        if (requestContext.hasEntity()) {
            reqLog.printBody(requestContext.getEntity().toString());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param requestContext  {@inheritDoc}
     * @param responseContext {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
            throws IOException {
        ResponseLogging resLog = new ResponseLogging((String) requestContext.getProperty(HEADER_TRACE_ID));

        printResponseHeaders(resLog, responseContext);
        printResponseBody(resLog, responseContext);
    }

    protected void printResponseHeaders(ResponseLogging resLog, ClientResponseContext responseContext) {
        resLog.printHeader(responseContext.getStatusInfo());
        resLog.printHeaders(responseContext.getHeaders());
    }

    protected void printResponseBody(ResponseLogging resLog, ClientResponseContext responseContext) {
        if (responseContext.hasEntity()) {
            resLog.printBody(responseContext.getEntityStream());
            responseContext.setEntityStream(resLog.getEntityStream());
        }
    }

}