package com.sacooliveros.escale.bean;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class ColegioAnio {

    private Colegio colegio;
    private int anio;
    //@TODO: Definir demas datos informativos para cada anio

    public Colegio getColegio() {
        return colegio;
    }

    public void setColegio(Colegio colegio) {
        this.colegio = colegio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
