package com.sacooliveros.client.rest.filters.logging;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class JerseyLogginFilter extends ClientFilter {
    @Override
    public ClientResponse handle(ClientRequest requestContext) throws ClientHandlerException {

        // Modify the request
        RequestLogging reqLog = new RequestLogging();

        reqLog.registerTrace(requestContext.getEntity());

        printRequestHeaders(reqLog, requestContext);

        printRequestBody(reqLog, requestContext);


        // Call the next client handler in the filter chain
        ClientResponse responseContext = getNext().handle(requestContext);

        // Modify the response
        ResponseLogging resLog = new ResponseLogging(reqLog.getTrace());

        printResponseHeaders(resLog, responseContext);

        printResponseBody(resLog, responseContext);

        return responseContext;
    }

    protected void printRequestHeaders(RequestLogging reqLog, ClientRequest requestContext) {
        reqLog.printHeader(requestContext.getMethod(), requestContext.getURI().toString());
        reqLog.printHeaders((MultivaluedMap) requestContext.getHeaders());
    }

    protected void printRequestBody(RequestLogging reqLog, ClientRequest requestContext) {
        if (requestContext.getEntity() != null) {
            reqLog.printBody(requestContext.getEntity().toString());
        }
    }

    protected void printResponseHeaders(ResponseLogging resLog, ClientResponse responseContext) {
        resLog.printHeader(responseContext.getStatusInfo());
        resLog.printHeaders(responseContext.getHeaders());
    }

    protected void printResponseBody(ResponseLogging resLog, ClientResponse responseContext) {
        if (responseContext.hasEntity()) {
            resLog.printBody(responseContext.getEntityInputStream());
            responseContext.setEntityInputStream(resLog.getEntityStream());
        }
    }
}
