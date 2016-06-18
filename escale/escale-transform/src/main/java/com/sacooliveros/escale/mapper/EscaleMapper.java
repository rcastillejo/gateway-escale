package com.sacooliveros.escale.mapper;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.dto.DetalleModalidad;
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.client.dto.InstitucionResponse;
import com.sacooliveros.escale.client.dto.InstitucionesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.dsig.spec.XPathType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class EscaleMapper {
    public static final Logger LOG = LoggerFactory.getLogger(EscaleMapper.class);

    public static EscaleMapper newInstace(){
        return new EscaleMapper();
    }

    public List<Colegio> mapFrom(InstitucionesResponse institucionesResponse) {
        List<Colegio> colegios = new ArrayList<Colegio>();

        LOG.debug("Evaluando response [{}]", institucionesResponse);
        if (institucionesResponse.getItems() != null && !institucionesResponse.getItems().isEmpty()) {
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

    public List<ColegioDetalle> mapFrom(InstitucionResponse institucionResponse, String anio){
        List<ColegioDetalle> detalle = null;
        int anioDetalle = Integer.parseInt(anio);
        if(institucionResponse.getInicial() != null) {
            detalle = mapFrom(institucionResponse.getCodigo(), anioDetalle,institucionResponse.getInicial());
        } else if(institucionResponse.getPrimaria() != null) {
            detalle = mapFrom(institucionResponse.getCodigo(), anioDetalle, institucionResponse.getPrimaria());
        } else if(institucionResponse.getSecundaria() != null) {
            detalle = mapFrom(institucionResponse.getCodigo(), anioDetalle, institucionResponse.getSecundaria());
        }
        return detalle;
    }



    public List<ColegioDetalle> mapFrom(String codigo, int anio, DetalleModalidad detalleModalidadResponse){
        int grado = 0;
        List<ColegioDetalle> inicialDetalle = new ArrayList<ColegioDetalle>();

        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr00()));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr01()));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr02()));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr03()));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr04()));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr05()));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr06()));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, ++grado, detalleModalidadResponse.getMatr07()));

        return inicialDetalle;
    }
}
