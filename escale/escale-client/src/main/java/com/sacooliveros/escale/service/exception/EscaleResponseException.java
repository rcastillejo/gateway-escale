package com.sacooliveros.escale.service.exception;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class EscaleResponseException extends RuntimeException{
    public EscaleResponseException() {
    }

    public EscaleResponseException(String message) {
        super(message);
    }

    public EscaleResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EscaleResponseException(Throwable cause) {
        super(cause);
    }
}

