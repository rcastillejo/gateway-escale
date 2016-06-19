/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.etl.message;

import com.sacooliveros.escale.bean.Colegio;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ricardo
 */
public class Mensaje {

    private String id;
    private Colegio colegio;
    private String[] years;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Colegio getColegio() {
        return colegio;
    }

    public void setColegio(Colegio colegio) {
        this.colegio = colegio;
    }

    public String[] getYears() {
        return years;
    }

    public void setYears(String[] year) {
        this.years = year;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id='" + id + '\'' +
                ", colegio=" + colegio +
                ", years=" + Arrays.toString(years) +
                '}';
    }
}
