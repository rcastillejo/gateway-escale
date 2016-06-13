package com.sacooliveros.escale.bean;


/**
 * Created by Ricardo on 04/06/2016.
 */
public class ColegioDetalle {


    private String codigoInstitucion;
    private int grado;
    private int anio;
    private int cantidad;

    public String getCodigoInstitucion() {
        return codigoInstitucion;
    }

    public void setCodigoInstitucion(String codigoInstitucion) {
        this.codigoInstitucion = codigoInstitucion;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ColegioDetalle{" +
                "codigoInstitucion='" + codigoInstitucion + '\'' +
                ", grado=" + grado +
                ", anio=" + anio +
                ", cantidad=" + cantidad +
                '}';
    }
}
