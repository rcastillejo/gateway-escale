package com.sacooliveros.escale.service;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.dao.EscaleClientService;
import com.sacooliveros.escale.mapper.EscaleMapper;
import com.sacooliveros.escale.dao.Filter;
import com.sacooliveros.escale.dao.dto.InstitucionResponse;
import com.sacooliveros.escale.dao.dto.InstitucionesResponse;

import java.util.List;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class EscaleService {


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

        filter.addLevels(colegio.getCodigoNivel());

        response = client.getInstituteDetails(colegio.getCodigo(), filter);
        colegioDetalle = escaleMapper.mapFrom(response);
        return colegioDetalle;
    }

    public void guardarColegios(List<Colegio> colegios) {
        for (Colegio colegio : colegios) {
            guardarColegio(colegio);
        }
    }

    public void guardarColegio(Colegio colegio) {

        Colegio colegioEncontrado = colegiodao.get(colegio.getCodigo());

        if(colegioEncontrado == null){
            colegiodao.insert(colegio);
        }else{
            colegiodao.update(colegio);
        }

    }
}
