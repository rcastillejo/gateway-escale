/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.dao;

import com.sacooliveros.escale.client.Filter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Ricardo
 */
public class FilterTest {

    private static final Logger log = LoggerFactory.getLogger(FilterTest.class);



    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddLevelsRetrieveEmptyList(){
        Filter filter = new Filter();

        filter.addLevels(null);
        assertTrue(filter.getLevels().isEmpty());

        filter.addLevels(new String[0]);
        assertTrue(filter.getLevels().isEmpty());


    }

    @Test
    public void testSetPrefixLevelsRetrieve(){
        Filter filter = new Filter();

        filter.addPrefixLevel("B1");
        assertEquals("B", filter.getLevels().get(0));


    }

}
