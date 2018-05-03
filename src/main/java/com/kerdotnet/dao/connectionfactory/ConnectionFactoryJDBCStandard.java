package com.kerdotnet.dao.connectionfactory;

import com.kerdotnet.exceptions.DAOConfigurationException;
import com.kerdotnet.exceptions.DAOSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Standard Connection Pool
 * Yevhen Ivanov, 2018-04-26
 */
public class ConnectionFactoryJDBCStandard implements ConnectionFactory{

    protected DataSource dataSource;
    protected Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactoryJDBCStandard.class);

    private ConnectionFactoryJDBCStandard() throws DAOConfigurationException {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            dataSource = (DataSource)
                    envCtx.lookup("jdbc/libraryDB");
            LOGGER.debug("DataSource was successfully initialized");
        } catch (Exception e) {
            throw new DAOConfigurationException("Error in DAO Factory configurations.", e);
        }
    }

    public static ConnectionFactory getInstance() {
        return ConnectionFactoryJDBCStandardSingleton.INSTANCE.get();
    }

    @Override
    public Connection getConnection() throws DAOSystemException {
        try {
            if (this.connection == null
                    || this.connection.isClosed()) {
                this.connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            throw new DAOSystemException("Abstract DAOManager exception. Error in connection opening", e);
        }
        return connection;
    }

    @Override
    public void closeConnection() throws DAOSystemException {
        try {
            if (this.connection != null
                    && !this.connection.isClosed())
                this.connection.close();
        } catch (SQLException e) {
            throw new DAOSystemException("Abstract DAOManager exception. Couldn't close connection", e);
        }
    }

    private static class ConnectionFactoryJDBCStandardSingleton {

        public static final ThreadLocal<ConnectionFactoryJDBCStandard> INSTANCE;

        static {
            ThreadLocal<ConnectionFactoryJDBCStandard> dm;
            try {
                dm = ThreadLocal.withInitial(() -> {
                    try {
                        return new ConnectionFactoryJDBCStandard();
                    } catch (Exception e) {
                        LOGGER.error("Unexpected error", e);
                        return null;
                    }
                });
            } catch (NullPointerException e) {
                LOGGER.error("Unexpected error", e);
                dm = null;
            }
            INSTANCE = dm;
        }
    }
}
