package com.kerdotnet.dao.factory;

import com.kerdotnet.dao.IDAO;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.exceptions.DAOSystemException;

/**
 * DAO Factory interface
 * Yevhen Ivanov, 2018-04-20
 */

public interface IDAOFactory extends AutoCloseable{
    IDAO getDAO(DAOEnum daoType) throws DAOSystemException;

    IUserDAO getUserDAO() throws DAOSystemException;
    IUserAuthorityDAO getUserAuthorityDAO() throws DAOSystemException;

    void close() throws DAOSystemException;
}
