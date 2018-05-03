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

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLDAOFactory.class);

    private MySQLDAOFactory() {
        connectionFactory = ConnectionFactoryFactory.newConnectionFactory();
    }

    public static IDAOFactory getInstance() {
        return DAOManagerSingleton.INSTANCE.get();
    }

    public IDAO getDAO(DAOEnum daoType) throws DAOSystemException {
        switch (daoType) {
            case USER:
                return new UserDAOImpl(connectionFactory.getConnection());
            case USER_AUTHORITY:
                return new UserAuthorityDAOImpl(connectionFactory.getConnection());
            case AUTHORITY:
                return new AuthorityDAOImpl(connectionFactory.getConnection());
            case AUTHOR:
                return new AuthorDAOImpl(connectionFactory.getConnection());
            case BOOK_CATALOG:
                return new BookCatalogDAOImpl(connectionFactory.getConnection());
            case BOOK_CATALOG_AUTHOR:
                return new BookCatalogAuthorDAOImpl(connectionFactory.getConnection());
            case BOOK_ITEM:
                return new BookItemDAOImpl(connectionFactory.getConnection());
            case BOOK_ITEM_USER:
                return new BookItemUserDAOImpl(connectionFactory.getConnection());
            case TRANSACTION:
                return new TransactionDAOImpl(connectionFactory.getConnection());
            default: {
                throw new DAOSystemException("Trying to link to an not existing IDAO.");
            }
        }

    }

    @Override
    public IUserDAO getUserDAO() throws DAOSystemException {
        return new UserDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public IUserAuthorityDAO getUserAuthorityDAO() throws DAOSystemException {
        return new UserAuthorityDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public IAuthorityDAO getAuthorityDAO() throws DAOSystemException {
        return new AuthorityDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public IBookCatalogDAO getBookCatalogDAO() throws DAOSystemException {
        return new BookCatalogDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public IBookCatalogAuthorDAO getBookCatalogAuthorDAO() throws DAOSystemException {
        return new BookCatalogAuthorDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public IAuthorDAO getAuthorDAO() throws DAOSystemException {
        return new AuthorDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public IBookItemDAO getBookItemDAO() throws DAOSystemException {
        return new BookItemDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public IBookItemUserDAO getBookItemUserDAO() throws DAOSystemException {
        return new BookItemUserDAOImpl(connectionFactory.getConnection());
    }

    @Override
    public ITransactionDAO getTransactionDAO() throws DAOSystemException {
        return new TransactionDAOImpl(connectionFactory.getConnection());
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
