/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.client.rest.filters.logging;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author rcastillejo
 */
public class BasicLoggingTest {
    public static final Logger log = LoggerFactory.getLogger(BasicLoggingTest.class);
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public BasicLoggingTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCreateObjValWith2ArgShouldReceiveCompleteArgs() {
        String pattern = "++";
        String trace = "";
        BasicLogging logging = new BasicLogging(log, pattern);

        Object[] result = logging.createObjectValues("hola", "mundo");
        assertNotNull(result);
        assertArrayEquals(result, new Object[]{trace, pattern, "hola", "mundo"});
    }

    @Test
    public void testCreateObjValWith3ArgShouldReceiveCompleteArgs() {
        String pattern = "++";
        String trace = "";
        BasicLogging logging = new BasicLogging(log, pattern);

        Object[] result = logging.createObjectValues("java");
        assertNotNull(result);
        assertArrayEquals(result, new Object[]{trace, pattern, "java"});
    }

    @Test
    public void testCreateObjValWithStatusShouldReceiveCompleteArgs() {
        String pattern = "++";
        String trace = "";
        BasicLogging logging = new BasicLogging(log, pattern);

        Object[] result = logging.createObjectValues(Response.Status.BAD_GATEWAY);
        assertNotNull(result);
        assertArrayEquals(result, new Object[]{trace, pattern,
                String.valueOf(Response.Status.BAD_GATEWAY.getStatusCode()),
                Response.Status.BAD_GATEWAY.getReasonPhrase()});
    }

    @Test
    public void testFormatVaWith3ArgShouldReceiveCompleteArgs() {
        List<String> values = new ArrayList<String>();
        values.add("hola");
        values.add("mundo");
        values.add("java");

        String pattern = "++";
        BasicLogging logging = new BasicLogging(log, pattern);

        String result = logging.formatValues(values);
        assertNotNull(result);
        assertThat(result, Is.is("hola;mundo;java"));
    }

    @Test
    public void testFormatValWithNullValuesShouldReceiveCompleteArgs() {

        String pattern = "++";
        BasicLogging logging = new BasicLogging(log, pattern);

        String result = logging.formatValues(null);
        assertNotNull(result);
        assertThat(result, Is.is(""));
    }

    @Test
    public void testFormatValWithEmptyValuesShouldReceiveCompleteArgs() {
        List<String> values = new ArrayList<String>();

        String pattern = "++";
        BasicLogging logging = new BasicLogging(log, pattern);

        String result = logging.formatValues(values);
        assertNotNull(result);
        assertThat(result, Is.is(""));
    }

    @Test
    public void testFormatHdrObjValsShouldReceiveString() {
        String pattern = "<<";
        String trace = "123456";
        BasicLogging logging = new BasicLogging(log, pattern);
        logging.setTrace(trace);

        String result = logging.formatHeaderObjectValues("hola", "mundo");
        assertNotNull(result);
        assertThat(result, Is.is("[123456] << hola: mundo"));
    }

    @Test
    public void testFormatHdrObjValsStatusShouldReceiveString() {
        String pattern = ">>";
        String trace = "123456";
        BasicLogging logging = new BasicLogging(log, pattern);
        logging.setTrace(trace);

        String result = logging.formatHeaderObjectValues(Response.Status.BAD_GATEWAY);
        assertNotNull(result);
        assertThat(result, Is.is("[123456] >> " +
                Response.Status.BAD_GATEWAY.getStatusCode() + ": " +
                Response.Status.BAD_GATEWAY.getReasonPhrase() + ""));
    }

    @Test
    public void testFormatBdyObjValsStatusShouldReceiveString() {
        String pattern = ">>";
        String trace = "123456";
        BasicLogging logging = new BasicLogging(log, pattern);
        logging.setTrace(trace);

        String result = logging.formatBodyObjectValues("Hola Mundo");
        assertNotNull(result);
        assertThat(result, Is.is("[123456] >> Hola Mundo"));
    }

}
