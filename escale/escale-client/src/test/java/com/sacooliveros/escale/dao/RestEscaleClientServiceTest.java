/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.dao;

import com.sacooliveros.client.rest.filters.logging.JerseyLogginFilter;
import com.sacooliveros.escale.client.EscaleClientServiceConfig;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.dao.dto.Institucion;
import com.sacooliveros.escale.dao.dto.InstitucionResponse;
import com.sacooliveros.escale.dao.dto.InstitucionesResponse;
import com.sacooliveros.escale.client.exception.EscaleConnectTimeoutException;
import com.sacooliveros.escale.client.exception.EscaleReadTimeoutException;
import com.sacooliveros.escale.client.rest.RestEscaleClientService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.junit.*;

import static org.junit.Assert.*;

import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ricardo
 */
public class RestEscaleClientServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RestEscaleClientServiceTest.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private RestEscaleClientService clientService;

    @Before
    public void setUp() {
        clientService = new RestEscaleClientService();
        clientService.setConfig(new EscaleClientServiceConfig());
        clientService.setClient(new Client());
        clientService.addFilter(new JerseyLogginFilter());

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInstituteCountWithoutFilterRetrieveGreaterThanZero(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 1000);
        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 1000);

        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathCount("cuenta");
        clientService.setConfig(config);

        int count = clientService.getInstitutesCount(filter);

        System.out.println("cantidad:"+count);
        assertTrue(count > 0);
    }


    @Test
    public void testInstituteCountWithLevelsRetrieveGreaterThanZero(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 1000);
        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 1000);

        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathCount("cuenta");
        clientService.setConfig(config);

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("1");

        int count = clientService.getInstitutesCount(filter);

        System.out.println("cantidad:"+count);
        assertTrue(count > 1);
    }

    @Test
    public void testInstituteCountThrowErrorIfIsConnTimeout(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 3000);

        config.setUrl("http://localhost:8080/padron/rest/instituciones");
        config.setPathCount("cuenta");
        clientService.setConfig(config);

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("3");


        expectedException.expect(EscaleConnectTimeoutException.class);

        int count = clientService.getInstitutesCount(filter);

    }

    @Ignore
    @Test
    public void testInstituteCountThrowErrorIfIsReadTimeout(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(DefaultClientConfig.PROPERTY_READ_TIMEOUT, 10);

        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathCount("cuenta");
        clientService.setConfig(config);

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("3");


        expectedException.expect(EscaleReadTimeoutException.class);

        int count = clientService.getInstitutesCount(filter);

    }

    @Test
    public void testInstitutesRetrieveSuccess(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 10);
        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10);

        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathInstitutes("");
        clientService.setConfig(config);

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("1");
        filter.setStart(0);

        InstitucionesResponse response = clientService.getInstitutes(filter);

        for (Institucion institute: response.getItems()) {
            System.out.println("institute:"+institute);
        }

        System.out.println("resultado:"+response);
        System.out.println("resultado tamanio:"+response.getItems().size());
        assertNotNull(response);
    }

    @Test
    public void testInstituteDetailsInicialRetrieveSuccess(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 10);
        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10);

        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathInstituteDetail("{0}/0/");
        clientService.setConfig(config);

        filter.setExpandLevel("5");
        filter.setYear("2014");
        filter.addLevels("A");

        InstitucionResponse response = clientService.getInstituteDetails("1625771", filter);

        System.out.println("resultado:"+response);

        assertNotNull(response.getInicial());
    }

    @Test
    public void testInstituteDetailsPrimariaRetrieveSuccess(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 10);
        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10);

        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathInstituteDetail("{0}/0/");
        clientService.setConfig(config);

        filter.setExpandLevel("5");
        filter.setYear("2007");
        filter.addLevels("B");

        InstitucionResponse response = clientService.getInstituteDetails("1238179", filter);

        System.out.println("resultado:"+response);

        assertNotNull(response.getPrimaria());
    }


    @Test
    public void testInstituteDetailsSecundariaRetrieveSuccess(){

        Filter filter = new Filter();
        ClientConfig clientConfig= new DefaultClientConfig();
        EscaleClientServiceConfig config = new EscaleClientServiceConfig();

        clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 10);
        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 10);

        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathInstituteDetail("{0}/0/");
        clientService.setConfig(config);

        filter.setExpandLevel("5");
        filter.setYear("2005");
        filter.addLevels("F");

        InstitucionResponse response = clientService.getInstituteDetails("0466730", filter);

        System.out.println("resultado:"+response);

        assertNotNull(response.getSecundaria());
    }
}
