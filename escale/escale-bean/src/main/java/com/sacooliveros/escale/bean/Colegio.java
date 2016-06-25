package com.sacooliveros.escale.bean;


import java.util.List;

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
    private double latitud;
    private double longitud;

    public Colegio() {
    }

    private List<ColegioDetalle> detalle;

    public List<ColegioDetalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<ColegioDetalle> detalle) {
        this.detalle = detalle;
    }

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
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", detalle=" + (detalle  == null ? detalle : detalle.size())  +
                '}';
    }
}
