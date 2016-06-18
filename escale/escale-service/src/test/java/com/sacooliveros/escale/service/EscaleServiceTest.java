/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.service;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.EscaleClientServiceConfig;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.rest.RestEscaleClientService;
import com.sacooliveros.escale.dao.mybatis.MyBatisDAOFactory;
import com.sacooliveros.escale.mapper.EscaleMapper;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author Ricardo
 */
public class EscaleServiceTest {

    private static final Logger log = LoggerFactory.getLogger(EscaleServiceTest.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private EscaleService escaleService;

    @Before
    public void setUp() {

        MyBatisDAOFactory.init("mybatis-config.xml");

        EscaleClientServiceConfig config = new EscaleClientServiceConfig();
        config.setRestConfig(new DefaultClientConfig());
        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathCount("cuenta");
        config.setPathInstitutes("");
        config.setPathInstituteDetail("{0}/0/");

        escaleService = new EscaleService(
                RestEscaleClientService.newInstance(config),
                EscaleMapper.newInstace(),
                MyBatisDAOFactory.getColegioDAO());

    }

    @After
    public void tearDown() {
    }


    @Test
    public void testSuccess() throws CloneNotSupportedException {

        Filter filter = new Filter();

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("1");
        filter.setStart(0);

        List<Colegio> colegios = escaleService.obtenerColegios(filter);
        log.debug("Colegios Obtenidos:" + colegios);

        for (Colegio colegio : colegios) {
            Filter filterDetalle = new Filter();
            filterDetalle.setExpandLevel("5");
            filterDetalle.setYear("2014");
            log.debug("Buscando detalle del colegio ["+filterDetalle+"] colegio[" + colegio + "]");
            List<ColegioDetalle> detalle = escaleService.obtenerDetalle(colegio, filterDetalle);
            log.debug("Detalle encontrado:" + detalle);
            colegio.setDetalle(detalle);
        }


        assertNotNull(colegios);
    }

    @Test
    public void testSaveSuccess() throws CloneNotSupportedException {

        Filter filter = new Filter();

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("1");
        filter.setStart(0);

        List<Colegio> colegios = escaleService.obtenerColegios(filter);
        log.debug("Colegios Obtenidos:" + colegios);


        for (Colegio colegio : colegios) {
            Filter filterDetalle = new Filter();
            filterDetalle.setExpandLevel("5");
            filterDetalle.setYear("2015");
            log.debug("Buscando detalle del colegio ["+filterDetalle+"] colegio[" + colegio + "]");
            List<ColegioDetalle> detalle = escaleService.obtenerDetalle(colegio, filterDetalle);
            log.debug("Detalle encontrado:" + detalle);
            colegio.setDetalle(detalle);


        }

        log.debug("Colegios a guardar:" + colegios);
        for (Colegio colegio : colegios) {
            log.debug("Guardando colegio:" + colegio);
            escaleService.guardarColegio(colegio);
        }

        assertNotNull(colegios);
    }

}
