package com.kerdotnet.exceptions;

/**
 * Exception for service layer; not unique user Login
 * Yevhen Ivanov, 2018-04-27
 */
public class NotUniqueUserLoginException extends DAOSystemException{
    public NotUniqueUserLoginException() {
    }

    public NotUniqueUserLoginException(String message) {
        super(message);
    }

    public NotUniqueUserLoginException(Throwable cause) {
        super(cause);
    }

    public NotUniqueUserLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
