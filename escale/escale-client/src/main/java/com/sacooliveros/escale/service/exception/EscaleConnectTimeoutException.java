package com.sacooliveros.escale.service.exception;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class EscaleConnectTimeoutException extends RuntimeException{
    public EscaleConnectTimeoutException() {
    }

    public EscaleConnectTimeoutException(String message) {
        super(message);
    }

    public EscaleConnectTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public EscaleConnectTimeoutException(Throwable cause) {
        super(cause);
    }
}

