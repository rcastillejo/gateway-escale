package com.sacooliveros.escale.service;

import com.sacooliveros.escale.service.dto.Institucion;
import com.sacooliveros.escale.service.dto.InstitucionesResponse;

import java.util.List;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class EscaleService {


    private EscaleClientService client;
    private EscaleTransform tranform;
    private InstitucionDAO dao;

    public int getCount(Filter filter) {
        return client.getInstitutesCount(filter);
    }

    public List processInstitutes(Filter filter) {
        List instituciones;
        InstitucionesResponse response;

        response = client.getInstitutes(filter);
        instituciones = tranform.getInstitutes(response);

        for (Institucion institucion : instituciones) {
            dao.insert(institucion);
        }

        return instituciones;
    }
}
