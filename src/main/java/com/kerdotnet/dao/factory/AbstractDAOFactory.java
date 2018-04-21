package com.kerdotnet.dao.factory;

import com.kerdotnet.exceptions.DAOSystemException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Common functionality for DAO Factory classes
 * Yevhen Ivanov, 2018-04-20
 */

public class AbstractDAOFactory {
    // List of DAO types supported by the factory
    public static final int MYSQL = 1;
    protected DataSource dataSource;
    protected Connection connection;

    static final Logger LOGGER = Logger.getLogger(AbstractDAOFactory.class);

    public static IDAOFactory getDAOFactory(
            int whichFactory) {
        switch (whichFactory) {
            case MYSQL:
                return MySQLDAOFactory.getInstance();
            default:
                return null;
        }
    }

    public void open() throws DAOSystemException {
        try {
            if (this.connection == null
                    || this.connection.isClosed())
                this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("IDAO Manager exception", e);
        }
    }

    public void close() throws DAOSystemException {
        try {
            if (this.connection != null
                    && !this.connection.isClosed())
                this.connection.close();
        } catch (SQLException e) {
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("IDAO Manager exception", e);
        }
    }
}
