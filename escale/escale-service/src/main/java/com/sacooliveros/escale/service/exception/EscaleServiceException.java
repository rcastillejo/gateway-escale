package com.sacooliveros.escale.service.exception;

/**
 * Created by Ricardo on 19/06/2016.
 */
public class EscaleServiceException extends RuntimeException{

    public EscaleServiceException() {
    }

    public EscaleServiceException(String message) {
        super(message);
    }

    public EscaleServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EscaleServiceException(Throwable cause) {
        super(cause);
    }
}
