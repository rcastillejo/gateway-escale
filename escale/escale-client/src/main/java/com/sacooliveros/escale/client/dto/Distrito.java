package com.sacooliveros.escale.client.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Ricardo on 05/06/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Distrito {
    @XmlElement(name = "idDistrito")
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Distrito{" +
                "codigo='" + codigo + '\'' +
                '}';
    }
}
