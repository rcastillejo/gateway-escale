package com.sacooliveros.escale.client.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Ricardo on 04/06/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Institucion {

    @XmlElement(name = "codMod")
    private String codigo;
    @XmlElement(name = "cenEdu")
    private String centroEducativo;
    @XmlElement(name = "codlocal")
    private String codigoLocal;

    @XmlElement(name = "nivelModalidad")
    private NivelModalidad nivelModalidad;

    @XmlElement(name = "gestionDependencia")
    private GestionDependencia gestionDependencia;


    @XmlElement(name = "dirCen")
    private String direccion;

    @XmlElement(name = "distrito")
    private Distrito distrito;


    @XmlElement(name = "nlatIE")
    private double latitud;
    @XmlElement(name = "nlongIE")
    private double longitud;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCentroEducativo() {
        return centroEducativo;
    }

    public void setCentroEducativo(String centroEducativo) {
        this.centroEducativo = centroEducativo;
    }

    public NivelModalidad getNivelModalidad() {
        return nivelModalidad;
    }

    public void setNivelModalidad(NivelModalidad nivelModalidad) {
        this.nivelModalidad = nivelModalidad;
    }

    public GestionDependencia getGestionDependencia() {
        return gestionDependencia;
    }

    public void setGestionDependencia(GestionDependencia gestionDependencia) {
        this.gestionDependencia = gestionDependencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(String codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    @Override
    public String toString() {
        return "Institucion{" +
                "codigo='" + codigo + '\'' +
                ", centroEducativo='" + centroEducativo + '\'' +
                ", codigoLocal='" + codigoLocal + '\'' +
                ", nivelModalidad=" + nivelModalidad +
                ", gestionDependencia=" + gestionDependencia +
                ", direccion='" + direccion + '\'' +
                ", distrito=" + distrito +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
