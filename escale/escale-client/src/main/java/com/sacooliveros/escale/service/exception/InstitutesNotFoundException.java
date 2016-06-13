package com.sacooliveros.escale.service.exception;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class InstitutesNotFoundException extends RuntimeException{
    public InstitutesNotFoundException() {
    }

    public InstitutesNotFoundException(String message) {
        super(message);
    }

    public InstitutesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstitutesNotFoundException(Throwable cause) {
        super(cause);
    }
}

