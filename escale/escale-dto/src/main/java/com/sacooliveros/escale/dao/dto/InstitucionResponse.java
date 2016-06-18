package com.sacooliveros.escale.dao.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Ricardo on 05/06/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "institucion")
public class InstitucionResponse extends Institucion {

    @XmlElement(name = "inicial")
    private DetalleModalidad inicial;

    @XmlElement(name = "primaria")
    private DetalleModalidad primaria;

    @XmlElement(name = "secundaria")
    private DetalleModalidad secundaria;

    public DetalleModalidad getInicial() {
        return inicial;
    }

    public void setInicial(DetalleModalidad inicial) {
        this.inicial = inicial;
    }

    public DetalleModalidad getPrimaria() {
        return primaria;
    }

    public void setPrimaria(DetalleModalidad primaria) {
        this.primaria = primaria;
    }

    public DetalleModalidad getSecundaria() {
        return secundaria;
    }

    public void setSecundaria(DetalleModalidad secundaria) {
        this.secundaria = secundaria;
    }

    @Override
    public String toString() {
        return "InstitucionResponse{" +
                "inicial=" + inicial +
                ", primaria=" + primaria +
                ", secundaria=" + secundaria +
                "} " + super.toString();
    }
}
