package com.sacooliveros.escale.service;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.client.EscaleClientService;
import com.sacooliveros.escale.mapper.EscaleMapper;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.InstitucionResponse;
import com.sacooliveros.escale.client.dto.InstitucionesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class EscaleService {
    public static final Logger LOG = LoggerFactory.getLogger(EscaleService.class);

    private EscaleClientService client;
    private EscaleMapper escaleMapper;
    private ColegioDAO colegiodao;

    public EscaleService(EscaleClientService client, EscaleMapper escaleMapper, ColegioDAO colegiodao) {
        this.client = client;
        this.escaleMapper = escaleMapper;
        this.colegiodao = colegiodao;
    }

    public int getCount(Filter filter) {
        return client.getInstitutesCount(filter);
    }

    public List<Colegio> obtenerColegios(Filter filter) {
        List<Colegio> colegios;
        InstitucionesResponse response;

        response = client.getInstitutes(filter);

        colegios = escaleMapper.mapFrom(response);

        return colegios;
    }

    public List<Colegio> obtenerDetalles(List<Colegio> colegios, Filter filter) {
        InstitucionesResponse response;

        for (Colegio colegio : colegios) {
            colegio.setDetalle(obtenerDetalle(colegio, filter));
        }
        return colegios;
    }

    public List<ColegioDetalle> obtenerDetalle(Colegio colegio, Filter filter) {
        List<ColegioDetalle> colegioDetalle;
        InstitucionResponse response;

        filter.addPrefixLevel(colegio.getCodigoNivel());

        response = client.getInstituteDetails(colegio.getCodigo(), filter);
        colegioDetalle = escaleMapper.mapFrom(response, filter.getYear());
        return colegioDetalle;
    }

    public void guardarColegios(List<Colegio> colegios) {
        for (Colegio colegio : colegios) {
            guardarColegio(colegio);
        }
    }

    public void guardarColegio(Colegio colegio) {

        Colegio colegioEncontrado = colegiodao.get(colegio.getCodigo());
        LOG.debug("colegio encontrado:"+colegioEncontrado);
        if(colegioEncontrado == null){
            colegiodao.insert(colegio);
        }else{
            colegiodao.update(colegio);
        }

    }
}
