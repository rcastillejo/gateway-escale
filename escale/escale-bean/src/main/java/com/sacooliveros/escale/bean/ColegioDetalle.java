package com.sacooliveros.escale.bean;


/**
 * Created by Ricardo on 04/06/2016.
 */
public class ColegioDetalle {


    private String codigoColegio;
    private int anio;
    private int grado;
    private int cantidad;
    private int codTipo;

    public ColegioDetalle() {
    }

    public ColegioDetalle(String codigoColegio, int anio, int grado, int cantidad, int codTipo) {
        this.codigoColegio = codigoColegio;
        this.anio = anio;
        this.grado = grado;
        this.cantidad = cantidad;
        this.codTipo = codTipo;
    }

    public ColegioDetalle(String codigoColegio, int anio, int grado, int codTipo) {
        this.codigoColegio = codigoColegio;
        this.anio = anio;
        this.grado = grado;
        this.cantidad = 0;
        this.codTipo = codTipo;
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

    public int getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(int codTipo) {
        this.codTipo = codTipo;
    }

    @Override
    public String toString() {
        return "ColegioDetalle{" +
                "codigoColegio='" + codigoColegio + '\'' +
                ", anio=" + anio +
                ", grado=" + grado +
                ", cantidad=" + cantidad +
                ", codTipo=" + codTipo +
                '}';
    }
}
