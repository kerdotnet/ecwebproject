package com.kerdotnet.exceptions;

import java.sql.SQLException;

/**Exceptions for DAO system
 * Yevhen Ivanov, 2018-04-12
 */

public class DAOSystemException extends SQLException{

    public DAOSystemException() {
    }

    public DAOSystemException(String message) {
        super(message);
    }

    public DAOSystemException(String message, SQLException e) {
        super(message, e);
    }
}
