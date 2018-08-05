package com.sacooliveros.client.rest.filters.logging;

import org.slf4j.Logger;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by rcastillejo on 11/05/2016.
 */
public class BasicLogging {

    private static final String SEPARATOR = ";";
    private static final String HEADER_LAYOUT = "[{0}] {1} {2}: {3}";
    private static final String BODY_LAYOUT = "[{0}] {1} {2}";

    private final Logger log;
    private final String pattern;
    private String trace;

    public BasicLogging(Logger log, String pattern) {
        this.log = log;
        this.pattern = pattern;
        this.trace = "";
    }

    protected String formatValues(List values) {
        StringBuilder sbValue = new StringBuilder();
        if (values != null && !values.isEmpty()) {
            for (Object value : values) {
                sbValue.append(value);
                sbValue.append(SEPARATOR);
            }
            return sbValue.substring(0, sbValue.length() - 1);
        } else {
            return sbValue.toString();
        }
    }

    protected Object[] createObjectValues(Response.StatusType statusType) {
        return createObjectValues(String.valueOf(statusType.getStatusCode()), statusType.getReasonPhrase());
    }

    protected Object[] createObjectValues(String name, String value) {
        return new Object[]{trace, pattern, name, value};
    }

    protected Object[] createObjectValues(String value) {
        return new Object[]{trace, pattern, value};
    }

    protected void printHeaders(MultivaluedMap<String, String> params) {
        for (Map.Entry<String, List<String>> param : params.entrySet()) {
            String name = param.getKey();
            String formatValues = formatValues(param.getValue());
            printHeader(name, formatValues);
        }
    }


    protected String formatHeaderObjectValues(Response.StatusType statusType) {
        return MessageFormat.format(HEADER_LAYOUT, createObjectValues(statusType));
    }

    protected String formatHeaderObjectValues(String name, String value) {
        return MessageFormat.format(HEADER_LAYOUT, createObjectValues(name, value));
    }

    protected String formatBodyObjectValues(String body) {
        return MessageFormat.format(BODY_LAYOUT, createObjectValues(body));
    }

    protected void registerTrace(Object entity){
    }


    protected void printHeader(Response.StatusType statusType) {
        log.debug(formatHeaderObjectValues(statusType));
    }

    protected void printHeader(String name, String value) {
        log.debug(formatHeaderObjectValues(name, value));
    }

    protected void printBody(String body) {
        log.debug(formatBodyObjectValues(body));
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getTrace() {
        return trace;
    }
}
