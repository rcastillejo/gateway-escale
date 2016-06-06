package com.sacooliveros.escale.client.bean;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Ricardo on 04/06/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Institute {

    @XmlElement(name = "codMod")
    private String codigo;
    @XmlElement(name = "cenEdu")
    private String centroEducativo;

    @XmlElement(name = "nivelModalidad")
    private NivelModalidad nivelModalidad;

    @XmlElement(name = "gestionDependencia")
    private GestionDependencia gestionDependencia;


    @XmlElement(name = "dirCen")
    private String direccion;

    @XmlElement(name = "distrito")
    private Distrito distrito;


    @XmlElement(name = "nlatIE")
    private String latitud;
    @XmlElement(name = "nlongIE")
    private String longitud;


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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Institute{" +
                "codigo='" + codigo + '\'' +
                ", centroEducativo='" + centroEducativo + '\'' +
                ", nivelModalidad=" + nivelModalidad +
                ", gestionDependencia=" + gestionDependencia +
                ", direccion='" + direccion + '\'' +
                ", distrito=" + distrito +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
