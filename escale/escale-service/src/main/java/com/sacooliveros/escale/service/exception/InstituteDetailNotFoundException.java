package com.sacooliveros.escale.service.exception;

/**
 * Created by Ricardo on 19/06/2016.
 */
public class InstituteDetailNotFoundException extends EscaleServiceException{

    private String codigoColegio;

    public InstituteDetailNotFoundException(String codigoColegio, String message) {
        super(message);
        this.codigoColegio = codigoColegio;
    }

    public String getCodigoColegio() {
        return codigoColegio;
    }
}
