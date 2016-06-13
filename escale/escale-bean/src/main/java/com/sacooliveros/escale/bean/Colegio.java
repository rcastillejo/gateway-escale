package com.sacooliveros.escale.bean;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class Colegio {


    private String codigo;
    private String codigoLocal;
    private String nombre;
    private String codigoNivel;
    private String gestion;
    private String direccion;
    private String ubigueo;
    private String latitud;
    private String longitud;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(String codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoNivel() {
        return codigoNivel;
    }

    public void setCodigoNivel(String codigoNivel) {
        this.codigoNivel = codigoNivel;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbigueo() {
        return ubigueo;
    }

    public void setUbigueo(String ubigueo) {
        this.ubigueo = ubigueo;
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
        return "Colegio{" +
                "codigo='" + codigo + '\'' +
                ", codigoLocal='" + codigoLocal + '\'' +
                ", nombre='" + nombre + '\'' +
                ", codigoNivel='" + codigoNivel + '\'' +
                ", gestion='" + gestion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ubigueo='" + ubigueo + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
