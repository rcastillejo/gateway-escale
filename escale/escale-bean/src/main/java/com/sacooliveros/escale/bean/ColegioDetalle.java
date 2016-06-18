package com.sacooliveros.escale.bean;


/**
 * Created by Ricardo on 04/06/2016.
 */
public class ColegioDetalle {


    private String codigoColegio;
    private int grado;
    private int anio;
    private int cantidad;

    public ColegioDetalle(int anio, int cantidad) {
        this.anio = anio;
        this.cantidad = cantidad;
    }

    public String getCodigoColegio() {
        return codigoColegio;
    }

    public void setCodigoColegio(String codigoColegio) {
        this.codigoColegio = codigoColegio;
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
                "codigoColegio='" + codigoColegio + '\'' +
                ", grado=" + grado +
                ", anio=" + anio +
                ", cantidad=" + cantidad +
                '}';
    }
}
