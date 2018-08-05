package com.sacooliveros.escale.etl.exception;

/**
 * Created by Ricardo on 09/07/2016.
 */
public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
