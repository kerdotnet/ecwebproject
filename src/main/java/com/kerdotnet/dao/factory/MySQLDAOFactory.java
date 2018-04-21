package com.kerdotnet.dao.factory;

import com.kerdotnet.dao.*;
import com.kerdotnet.dao.MySQLImplementation.*;
import com.kerdotnet.exceptions.DAOConfigurationException;
import com.kerdotnet.exceptions.DAOSystemException;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * IDAO manager
 * Establish data source
 * and is a fabric for DAOEntities
 * Yevhen Ivanov 2018-04-11
 */

public class MySQLDAOFactory extends AbstractDAOFactory implements IDAOFactory {

    static final Logger LOGGER = Logger.getLogger(MySQLDAOFactory.class);

    private MySQLDAOFactory() throws DAOConfigurationException {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            dataSource = (DataSource)
                    envCtx.lookup("jdbc/libraryDB");
            LOGGER.debug("DataSource was successfully initialized");
        } catch (Exception e) {
            LOGGER.error("Unexpected error", e);
            throw new DAOConfigurationException("Eror in DAO Factory configuration", e);
        }
    }

    public static IDAOFactory getInstance() {
        return DAOManagerSingleton.INSTANCE.get();
    }

    public IDAO getDAO(DAOEnum daoType) throws DAOSystemException {
        open();
        switch (daoType) {
            case USER:
                return new UserDAOImpl(this.connection);
            case USER_AUTHORITY:
                return new UserAuthorityDAOImpl(this.connection);
            case AUTHORITY:
                return new AuthorityDAOImpl(this.connection);
            case AUTHOR:
                return new AuthorDAOImpl(this.connection);
            case BOOK_CATALOG:
                return new BookCatalogDAOImpl(this.connection);
            case BOOK_CATALOG_AUTHOR:
                return new BookCatalogAuthorDAOImpl(this.connection);
            case BOOK_ITEM:
                return new BookItemDAOImpl(this.connection);
            case BOOK_ITEM_USER:
                return new BookItemUserDAOImpl(this.connection);
            case TRANSACTION:
                return new TransactionDAOImpl(this.connection);
            default: {
                LOGGER.error("Trying to link to a not existing IDAO.");
                throw new DAOSystemException("Trying to link to an not existing IDAO.");
            }
        }

    }

    @Override
    public IUserDAO getUserDAO() throws DAOSystemException {
        open();
        return new UserDAOImpl(this.connection);
    }

    @Override
    public IUserAuthorityDAO getUserAuthorityDAO() throws DAOSystemException {
        open();
        return new UserAuthorityDAOImpl(this.connection);
    }

    @Override
    public IAuthorityDAO getAuthorityDAO() throws DAOSystemException {
        open();
        return new AuthorityDAOImpl(this.connection);
    }

    @Override
    public IBookCatalogDAO getBookCatalogDAO() throws DAOSystemException {
        open();
        return new BookCatalogDAOImpl(this.connection);
    }

    @Override
    public IBookCatalogAuthorDAO getBookCatalogAuthorDAO() throws DAOSystemException {
        open();
        return new BookCatalogAuthorDAOImpl(this.connection);
    }

    @Override
    public IAuthorDAO getAuthorDAO() throws DAOSystemException {
        open();
        return new AuthorDAOImpl(this.connection);
    }

    @Override
    public IBookItemDAO getBookItemDAO() throws DAOSystemException {
        open();
        return new BookItemDAOImpl(this.connection);
    }

    @Override
    public IBookItemUserDAO getBookItemUserDAO() throws DAOSystemException {
        open();
        return new BookItemUserDAOImpl(this.connection);
    }

    @Override
    public ITransactionDAO getTransactionDAO() throws DAOSystemException {
        open();
        return new TransactionDAOImpl(this.connection);
    }

    private static class DAOManagerSingleton {

        public static final ThreadLocal<MySQLDAOFactory> INSTANCE;

        static {
            ThreadLocal<MySQLDAOFactory> dm;
            try {
                dm = ThreadLocal.withInitial(() -> {
                    try {
                        return new MySQLDAOFactory();
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
