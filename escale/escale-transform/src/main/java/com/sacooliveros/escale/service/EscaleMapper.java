package com.sacooliveros.escale.service;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.service.dto.Institucion;
import com.sacooliveros.escale.service.dto.InstitucionResponse;
import com.sacooliveros.escale.service.dto.InstitucionesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class EscaleMapper {

    public List<Colegio> mapFrom(InstitucionesResponse institucionesResponse) {
        List<Colegio> colegios = new ArrayList<Colegio>();

        if (institucionesResponse.getItems() != null && institucionesResponse.getItems().isEmpty()) {
            for (Institucion institucion : institucionesResponse.getItems()) {
                Colegio colegio = new Colegio();
                colegio.setCodigo(institucion.getCodigo());
                colegio.setCodigo(institucion.getCodigo());
                colegio.setCodigo(institucion.getCodigo());
                colegio.setCodigo(institucion.getCodigo());
                colegio.setCodigo(institucion.getCodigo());
                colegios.add(colegio);
            }
        }

        return colegios;
    }

    public Colegio mapFrom(InstitucionResponse institucionResponse){
        Colegio colegio = new Colegio();
        colegio.setCodigo(institucionResponse.getCodigo());
        colegio.setCodigoLocal(institucionResponse.getCodigoLocal());
        colegio.setNombre(institucionResponse.getCentroEducativo());
        //@TODO: Mapear codigo
        colegio.setCodigoNivel(institucionResponse.getNivelModalidad().getCodigo());

        colegio.setGestion(institucionResponse.getGestionDependencia().getDescripcion());
        colegio.setDireccion(institucionResponse.getDireccion());
        colegio.setUbigueo(institucionResponse.getDistrito().getCodigo());
        colegio.setLatitud(institucionResponse.getLatitud());
        colegio.setLongitud(institucionResponse.getLongitud());
        return colegio;
    }

}
