package com.kerdotnet.dao.factory;

import com.kerdotnet.controllers.Controller;
import com.kerdotnet.dao.AbstractDAO;
import com.kerdotnet.dao.UserAuthorityDAO;
import com.kerdotnet.dao.UserDAO;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DAO manager
 * Establish data source
 * and is a fabric for DAOEntities
 * Yevhen Ivanov 2018-04-11
 */

public class DAOManager {

    private DataSource dataSource;
    private Connection connection;
    static final Logger LOGGER = Logger.getLogger(Controller.class);

    private DAOManager() throws Exception {
        try
        {
            Context initContext = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/libraryDB");

            LOGGER.info("DataSource was successfully initialized");
        }
        catch(Exception e) {
            LOGGER.error("Unexpected error", e);
            throw e;
        }
    }

    public static DAOManager getInstance() {
        return DAOManagerSingleton.INSTANCE.get();
    }

    public void open() throws SQLException {
        try
        {
            if(this.connection == null
                    || this.connection.isClosed())
                this.connection = dataSource.getConnection();
        }
        catch(SQLException e) {
            LOGGER.error("Unexpected error", e);
            throw e;
        }
    }

    public void close() throws SQLException {
        try
        {
            if(this.connection != null
                    && !this.connection.isClosed())
                this.connection.close();
        }
        catch(SQLException e) {
            LOGGER.error("Unexpected error", e);
            throw e;
        }
    }

    private static class DAOManagerSingleton {

        public static final ThreadLocal<DAOManager> INSTANCE;
        static
        {
            ThreadLocal<DAOManager> dm;
            try
            {
                dm = ThreadLocal.withInitial(() -> {
                    try
                    {
                        return new DAOManager();
                    }
                    catch(Exception e)
                    {
                        LOGGER.error("Unexpected error", e);
                        return null;
                    }
                });
            }
            catch(NullPointerException e) {
                LOGGER.error("Unexpected error", e);
                dm = null;
            }
            INSTANCE = dm;
        }
    }

    public AbstractDAO getDAO(DAOEnum daoType) throws SQLException
    {

        try
        {
            if(this.connection == null ||
                    this.connection.isClosed())
                this.open();
        }
        catch(SQLException e){
            LOGGER.error("Unexpected error", e);
            throw e;
        }

        switch(daoType)
        {
            case USER:
                return new UserDAO(this.connection);
            case USER_AUTHORITY:
                return new UserAuthorityDAO(this.connection);
            default:{
                LOGGER.error("Trying to link to a not existing DAO.");
                throw new SQLException("Trying to link to an not existing DAO.");
            }
        }

    }
}
