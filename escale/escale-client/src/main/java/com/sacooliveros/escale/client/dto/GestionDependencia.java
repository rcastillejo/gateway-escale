package com.sacooliveros.escale.client.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Ricardo on 05/06/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GestionDependencia {
    @XmlElement(name = "idCodigo")
    private String codigo;

    @XmlElement(name = "valor")
    private String descripcion;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "GestionDependencia{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
