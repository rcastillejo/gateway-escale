package com.sacooliveros.escale.service.exception;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class ResponseMalformatException extends RuntimeException{
    public ResponseMalformatException() {
    }

    public ResponseMalformatException(String message) {
        super(message);
    }

    public ResponseMalformatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseMalformatException(Throwable cause) {
        super(cause);
    }
}

