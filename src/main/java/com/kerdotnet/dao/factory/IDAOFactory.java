package com.kerdotnet.dao.factory;

import com.kerdotnet.dao.*;
import com.kerdotnet.exceptions.DAOSystemException;

/**
 * DAO Factory interface
 * Yevhen Ivanov, 2018-04-20
 */

public interface IDAOFactory extends AutoCloseable{
    IDAO getDAO(DAOEnum daoType) throws DAOSystemException;

    IUserDAO getUserDAO() throws DAOSystemException;
    IAuthorityDAO getAuthorityDAO() throws DAOSystemException;
    IUserAuthorityDAO getUserAuthorityDAO() throws DAOSystemException;
    IBookCatalogDAO getBookCatalogDAO() throws DAOSystemException;
    IBookCatalogAuthorDAO getBookCatalogAuthorDAO() throws DAOSystemException;
    IAuthorDAO getAuthorDAO() throws DAOSystemException;
    IBookItemDAO getBookItemDAO() throws DAOSystemException;
    IBookItemUserDAO getBookItemUserDAO() throws DAOSystemException;
    ITransactionDAO getTransactionDAO() throws DAOSystemException;

    void close() throws DAOSystemException;
}
