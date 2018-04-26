package com.kerdotnet.dao.factory;

import com.kerdotnet.exceptions.DAOSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Common functionality for DAO Factory classes
 * Yevhen Ivanov, 2018-04-20
 */

public abstract class AbstractDAOFactory {
    // List of DAO types supported by the factory
    public static final int MYSQL = 1;
    protected DataSource dataSource;
    protected Connection connection;

    static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAOFactory.class);

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
                    || this.connection.isClosed()) {
                this.connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            throw new DAOSystemException("Abstract DAOManager exception. Error in connection opening", e);
        }
    }

    public void close() throws DAOSystemException {
        try {
            if (this.connection != null
                    && !this.connection.isClosed())
                this.connection.close();
        } catch (SQLException e) {
            throw new DAOSystemException("Abstract DAOManager exception. Couldn't close connection", e);
        }
    }
}
