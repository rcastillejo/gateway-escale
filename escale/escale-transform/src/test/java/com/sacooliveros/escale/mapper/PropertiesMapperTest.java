/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.mapper;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertThat;

/**
 * @author Ricardo
 */
public class PropertiesMapperTest {

    private static final Logger log = LoggerFactory.getLogger(PropertiesMapperTest.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private PropertiesMapper mapper;

    @Before
    public void setUp() throws IOException {
        InputStream is = PropertiesMapperTest.class.getClassLoader().getResourceAsStream("broker.properties");

        Properties config = new Properties();
        config.load(is);

        mapper = new PropertiesMapper(config);

    }

    @After
    public void tearDown() {
    }


    @Test
    public void testNivelSuccess()  {

        assertThat(mapper.getNivel("A0"), Is.is("1"));
        assertThat(mapper.getNivel("B0"), Is.is("2"));
        assertThat(mapper.getNivel("F0"), Is.is("3"));
    }


    @Test
    public void testNivelErrorNotExists()  {

        assertThat(mapper.getNivel("NOEXISTE"), Is.is("NOEXISTE"));
    }


    @Test
    public void testTipoDetalleSuccess()  {

        assertThat(mapper.getTipoDetalle(),  Is.is(3));
    }



}
