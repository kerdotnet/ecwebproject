package com.kerdotnet.exceptions;

/**
 * Exception for service layer; not unique user email
 * Yevhen Ivanov, 2018-04-27
 */
public class NotUniqueUserEmailException  extends DAOSystemException{
    public NotUniqueUserEmailException() {
    }

    public NotUniqueUserEmailException(String message) {
        super(message);
    }

    public NotUniqueUserEmailException(Throwable cause) {
        super(cause);
    }

    public NotUniqueUserEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
