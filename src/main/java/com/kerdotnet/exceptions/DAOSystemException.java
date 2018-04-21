package com.kerdotnet.exceptions;

import java.sql.SQLException;

/**Exceptions for IDAO system
 * Represents a generic DAO exception.
 * Yevhen Ivanov, 2018-04-12
 */

public class DAOSystemException extends SQLException{

    public DAOSystemException() {
    }

    public DAOSystemException(String message) {
        super(message);
    }

    public DAOSystemException(Throwable cause) {
        super(cause);
    }

    public DAOSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
