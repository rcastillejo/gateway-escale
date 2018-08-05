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
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.client.rest.RestEscaleClientService;
import com.sacooliveros.escale.dao.mybatis.MyBatisDAOFactory;
import com.sacooliveros.escale.mapper.EscaleMapper;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
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
    public void setUp() throws IOException {

        MyBatisDAOFactory daoFactory = new MyBatisDAOFactory("mybatis-config.xml");

        EscaleClientServiceConfig config = new EscaleClientServiceConfig();
        config.setRestConfig(new DefaultClientConfig());
        config.setUrl("http://escale.minedu.gob.pe/padron/rest/instituciones");
        config.setPathCount("cuenta");
        config.setPathInstitutes("");
        config.setPathInstituteDetail("{0}/0/");


        InputStream is = EscaleServiceTest.class.getClassLoader().getResourceAsStream("broker.properties");

        Properties properties = new Properties();
        properties.load(is);

        escaleService = new EscaleService(
                RestEscaleClientService.newInstance(config),
                EscaleMapper.newInstace(properties),
                daoFactory.getColegioDAO(),
                50);

    }

    @After
    public void tearDown() {
    }

    @Ignore
    @Test
    public void testSuccess() throws CloneNotSupportedException {

        Filter filter = new Filter();

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("1");
        filter.setStart(0);

        List<Institucion> colegios = escaleService.consultarSiguienteGrupoColegios(filter);
        log.debug("Colegios Obtenidos:" + colegios);


        Filter filterDetalle = new Filter();
        filterDetalle.setExpandLevel("5");
        filterDetalle.setYear("2014");
        log.debug("Buscando detalle del colegio [" + filterDetalle + "]");

        assertNotNull(colegios);
    }

    @Ignore
    @Test
    public void testSaveSuccess() throws CloneNotSupportedException {

        Filter filter = new Filter();

        filter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        filter.addStates("1");
        filter.setStart(0);

        List<Institucion> colegios = escaleService.consultarSiguienteGrupoColegios(filter);
        log.debug("Colegios Obtenidos:" + colegios);


        Filter filterDetalle = new Filter();
        filterDetalle.setExpandLevel("5");
        filterDetalle.setYear("2004");


        for (int i = 0 ; i < 2; i++) {
            Institucion colegio = colegios.get(i);
            log.debug("Buscando detalle del colegio ["+colegio.getCodigo()+"] [" + filterDetalle + "]");
            escaleService.consultarDetalleColegio(colegio, filterDetalle);

            log.debug("Colegio a guardar:" + colegio);
            escaleService.transformarGuardarColegio(colegio);
        }

        assertNotNull(colegios);
    }


    @Ignore
    @Test
    public void testInstitutesPagintationRetrieveSuccess() {

        Filter readerFilter;
        Filter workerFilter;


        readerFilter = new Filter();
        readerFilter.addLevels("A1", "A2", "A3", "A5", "B0", "F0");
        readerFilter.addStates("1");

        int count = escaleService.calcularTotalColegios(readerFilter);

        int c = 0;

        while (escaleService.existeColegiosPorConsultar() && c < 200) {
            //Hilo de lectura
            List<Institucion> colegios = escaleService.consultarSiguienteGrupoColegios(readerFilter);

            //Worker
            workerFilter = new Filter();
            workerFilter.setExpandLevel("5");
            workerFilter.setYear("2014");
            for (int i = 0 ; i < 2; i++) {
                Institucion colegio = colegios.get(i);
                escaleService.transformarGuardarColegio(colegio);

                List<ColegioDetalle> detalles = escaleService.consultarDetalleColegio(colegio, workerFilter);
                escaleService.guardarDetalleColegio(detalles);
            }
            c += colegios.size();
        }

        System.out.println("count:" + count + " vs c:" + c);
        assertEquals(200, c);
    }

}
