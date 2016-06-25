package com.sacooliveros.escale.mapper;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.dto.DetalleModalidad;
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.client.dto.InstitucionResponse;
import com.sacooliveros.escale.client.dto.InstitucionesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class EscaleMapper {
    public static final int INICIAL_INDICE_INICIAL = 1;
    public static final int PRIMARIA_INDICE_INICIAL = 9; // + 8 Grados de Inicial
    public static final int SECUNDARIA_INDICE_INICIAL = 15; // + 6 Grados de Primaria
    public static final Logger LOG = LoggerFactory.getLogger(EscaleMapper.class);

    private PropertiesMapper propertiesMapper;

    public static EscaleMapper newInstace(Properties config){
        return new EscaleMapper(new PropertiesMapper(config));
    }

    public EscaleMapper(PropertiesMapper propertiesMapper) {
        this.propertiesMapper = propertiesMapper;
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

        String escaleNivel = institucionResponse.getNivelModalidad().getCodigo();
        String colegioNivel = propertiesMapper.getNivel(escaleNivel);

        colegio.setCodigoNivel(colegioNivel);

        colegio.setGestion(institucionResponse.getGestionDependencia().getDescripcion());
        colegio.setDireccion(institucionResponse.getDireccion());
        colegio.setUbigueo(institucionResponse.getDistrito().getCodigo());
        colegio.setLatitud(institucionResponse.getLatitud());
        colegio.setAltitud(institucionResponse.getLongitud());

        return colegio;
    }

    public List<ColegioDetalle> mapFrom(InstitucionResponse institucionResponse, String anio){
        List<ColegioDetalle> detalle = null;
        int anioDetalle = Integer.parseInt(anio);
        int codigoTipo = propertiesMapper.getTipoDetalle();
        if(institucionResponse.getInicial() != null) {
            detalle = mapInicial(institucionResponse.getCodigo(), anioDetalle,institucionResponse.getInicial(), codigoTipo);
        } else if(institucionResponse.getPrimaria() != null) {
            detalle = mapPrimaria(institucionResponse.getCodigo(), anioDetalle, institucionResponse.getPrimaria(), codigoTipo);
        } else if(institucionResponse.getSecundaria() != null) {
            detalle = mapSecundaria(institucionResponse.getCodigo(), anioDetalle, institucionResponse.getSecundaria(), codigoTipo);
        }
        return detalle;
    }


    private List<ColegioDetalle> mapInicial(String codigo, int anio, DetalleModalidad detalleModalidadResponse, int codigoTipo){
        int grado = INICIAL_INDICE_INICIAL;
        List<ColegioDetalle> inicialDetalle = new ArrayList<ColegioDetalle>();
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr00(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr01(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr02(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr03(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr04(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr05(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr06(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr07(), codigoTipo));
        return inicialDetalle;
    }

    private List<ColegioDetalle> mapPrimaria(String codigo, int anio, DetalleModalidad detalleModalidadResponse, int codigoTipo){
        int grado = PRIMARIA_INDICE_INICIAL;
        List<ColegioDetalle> inicialDetalle = new ArrayList<ColegioDetalle>();
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr01(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr02(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr03(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr04(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr05(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr06(), codigoTipo));
        return inicialDetalle;
    }


    private List<ColegioDetalle> mapSecundaria(String codigo, int anio, DetalleModalidad detalleModalidadResponse, int codigoTipo){
        int grado = SECUNDARIA_INDICE_INICIAL;
        List<ColegioDetalle> inicialDetalle = new ArrayList<ColegioDetalle>();
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr01(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr02(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr03(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr04(), codigoTipo));
        inicialDetalle.add(new ColegioDetalle(codigo, anio, grado++, detalleModalidadResponse.getMatr05(), codigoTipo));
        return inicialDetalle;
    }
}
