package com.sacooliveros.escale.client.exception;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class EscaleReadTimeoutException extends RuntimeException{
    public EscaleReadTimeoutException(String message) {
        super(message);
    }

    public EscaleReadTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public EscaleReadTimeoutException(Throwable cause) {
        super(cause);
    }
}

