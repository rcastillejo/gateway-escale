package com.sacooliveros.escale.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by Ricardo on 25/06/2016.
 */
public class PropertiesMapper {
    public static final Logger LOG = LoggerFactory.getLogger(PropertiesMapper.class);
    private static final String PREFIX_NIVEL = "escale.mapper.nivel.";
    private static final String TIPO_DETALLE = "escale.mapper.tipoDetalle";
    private static final int DEFAULT_TIPO_DETALLE = 1;
    private Properties properties;

    public PropertiesMapper(Properties properties) {
        this.properties = properties;
    }

    public String getNivel(String codigoNivel){
        return properties.getProperty(PREFIX_NIVEL + codigoNivel, codigoNivel);
    }

    public int getTipoDetalle(){
        try {
            return properties.containsKey(TIPO_DETALLE) ? Integer.parseInt(properties.getProperty(TIPO_DETALLE)) : DEFAULT_TIPO_DETALLE;
        }catch (Exception e){
            LOG.warn("No se pudo leer el parametro ["+TIPO_DETALLE+"]", e);
            return DEFAULT_TIPO_DETALLE;
        }
    }
}
