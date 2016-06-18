package com.sacooliveros.escale.mapper;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.dao.dto.DetalleModalidad;
import com.sacooliveros.escale.dao.dto.Institucion;
import com.sacooliveros.escale.dao.dto.InstitucionResponse;
import com.sacooliveros.escale.dao.dto.InstitucionesResponse;

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
                Colegio colegio = mapFrom(institucion);
                colegios.add(colegio);
            }
        }

        return colegios;
    }

    public Colegio mapFrom(Institucion institucionResponse){
        Colegio colegio = new Colegio();
        colegio.setCodigo(institucionResponse.getCodigo());
        colegio.setCodigoLocal(institucionResponse.getCodigoLocal());
        colegio.setNombre(institucionResponse.getCentroEducativo());

        colegio.setCodigoNivel(institucionResponse.getNivelModalidad().getCodigo());

        colegio.setGestion(institucionResponse.getGestionDependencia().getDescripcion());
        colegio.setDireccion(institucionResponse.getDireccion());
        colegio.setUbigueo(institucionResponse.getDistrito().getCodigo());
        colegio.setLatitud(institucionResponse.getLatitud());
        colegio.setLongitud(institucionResponse.getLongitud());

        return colegio;
    }

    public List<ColegioDetalle> mapFrom(InstitucionResponse institucionResponse){
        List<ColegioDetalle> detalle;


        if(institucionResponse.getInicial() != null) {
            detalle = mapFrom(institucionResponse.getInicial());
        } else if(institucionResponse.getPrimaria() != null) {
            detalle = mapFrom(institucionResponse.getPrimaria());
        } else {
            detalle = mapFrom(institucionResponse.getSecundaria());
        }

        return detalle;
    }



    public List<ColegioDetalle> mapFrom(DetalleModalidad detalleModalidadResponse){
        int grado = 0;
        List<ColegioDetalle> inicialDetalle = new ArrayList<ColegioDetalle>();

        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr00()));
        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr01()));
        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr02()));
        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr03()));
        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr04()));
        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr05()));
        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr06()));
        inicialDetalle.add(new ColegioDetalle(++grado, detalleModalidadResponse.getMatr07()));

        return inicialDetalle;
    }
}
