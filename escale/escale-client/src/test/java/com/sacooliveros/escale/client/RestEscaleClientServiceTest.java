/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.client;

import java.util.Date;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ricardo
 */
public class RestEscaleClientServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RestEscaleClientServiceTest.class);

    private EscaleClientService clientService;

    @Before
    public void setUp() {
        clientService = new RestEscaleClientService();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInstituteCountRetrieveGreaterThanZero(){

        Filter filter = new Filter();

        filter.addLevels("A1", "A2", "A3", "B0", "F0");
        filter.addStates("A1", "A2", "A3", "B0", "F0");

        /*int count = clientService.getInstitutesCount(filter);
        assertTrue(count > 0);*/
    }

}
