package com.kerdotnet.exceptions;

/**
 * This class represents an exception in the DAO configuration
 * which cannot be resolved at runtime,
 * such as a missing resources or a missing property in the properties file
 * Yevhen Ivanov, 2018-04-21
 */

public class DAOConfigurationException extends RuntimeException{
    public DAOConfigurationException() {
    }

    public DAOConfigurationException(String message) {
        super(message);
    }

    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }

    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
