package com.kerdotnet.dao.daofactory;

import com.kerdotnet.dao.*;
import com.kerdotnet.dao.connectionfactory.ConnectionFactoryFactory;
import com.kerdotnet.dao.mysqlimplementation.*;
import com.kerdotnet.exceptions.DAOSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IDAO manager
 * Establish data source
 * and is a fabric for DAOEntities
 * Yevhen Ivanov 2018-04-11
 */

public class MySQLDAOFactory extends AbstractDAOFactory implements IDAOFactory {

    static final Logger LOGGER = LoggerFactory.getLogger(MySQLDAOFactory.class);

    private MySQLDAOFactory() throws DAOSystemException {
        connectionFactory = ConnectionFactoryFactory.newConnectionFactory();
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
