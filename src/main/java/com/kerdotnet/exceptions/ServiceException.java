package com.kerdotnet.exceptions;


/**
 * Exception for service layer
 * Yevhen Ivanov, 2018-04-27
 */
public class ServiceException extends Exception{

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
