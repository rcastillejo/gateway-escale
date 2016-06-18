package com.sacooliveros.escale.dao.exception;

/**
 * Created by Ricardo on 17/06/2016.
 */
public class DAOException extends RuntimeException{

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }


}
