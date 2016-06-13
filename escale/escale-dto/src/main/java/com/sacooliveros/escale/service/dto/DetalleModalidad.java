package com.sacooliveros.escale.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Ricardo on 05/06/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DetalleModalidad {
    @XmlElement(name = "matr00")
    private int matr00;
    @XmlElement(name = "matr01")
    private int matr01;
    @XmlElement(name = "matr02")
    private int matr02;
    @XmlElement(name = "matr03")
    private int matr03;
    @XmlElement(name = "matr04")
    private int matr04;
    @XmlElement(name = "matr05")
    private int matr05;
    @XmlElement(name = "matr06")
    private int matr06;
    @XmlElement(name = "matr07")
    private int matr07;

    public int getMatr00() {
        return matr00;
    }

    public void setMatr00(int matr00) {
        this.matr00 = matr00;
    }

    public int getMatr01() {
        return matr01;
    }

    public void setMatr01(int matr01) {
        this.matr01 = matr01;
    }

    public int getMatr02() {
        return matr02;
    }

    public void setMatr02(int matr02) {
        this.matr02 = matr02;
    }

    public int getMatr03() {
        return matr03;
    }

    public void setMatr03(int matr03) {
        this.matr03 = matr03;
    }

    public int getMatr04() {
        return matr04;
    }

    public void setMatr04(int matr04) {
        this.matr04 = matr04;
    }

    public int getMatr05() {
        return matr05;
    }

    public void setMatr05(int matr05) {
        this.matr05 = matr05;
    }

    public int getMatr06() {
        return matr06;
    }

    public void setMatr06(int matr06) {
        this.matr06 = matr06;
    }

    public int getMatr07() {
        return matr07;
    }

    public void setMatr07(int matr07) {
        this.matr07 = matr07;
    }

    @Override
    public String toString() {
        return "DetalleModalidad{" +
                "matr00=" + matr00 +
                ", matr01=" + matr01 +
                ", matr02=" + matr02 +
                ", matr03=" + matr03 +
                ", matr04=" + matr04 +
                ", matr05=" + matr05 +
                ", matr06=" + matr06 +
                ", matr07=" + matr07 +
                '}';
    }
}
