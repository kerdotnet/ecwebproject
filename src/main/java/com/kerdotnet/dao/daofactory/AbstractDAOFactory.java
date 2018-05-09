package com.kerdotnet.dao.daofactory;

import com.kerdotnet.dao.connectionfactory.ConnectionFactory;
import com.kerdotnet.exceptions.DAOSystemException;

/**
 * Common functionality for DAO Factory classes
 * Yevhen Ivanov, 2018-04-20
 */

public abstract class AbstractDAOFactory {
    // List of DAO types supported by the daofactory
    public enum DAOFactoryFactoryType {MY_SQL}
    private static DAOFactoryFactoryType currentType = DAOFactoryFactoryType.MY_SQL;

    public static IDAOFactory getDAOFactory() throws DAOSystemException {
        IDAOFactory result;
        try {
            switch (currentType){
                case MY_SQL:
                    result = MySQLDAOFactory.getInstance();
                    break;
                default:
                    throw new RuntimeException("DAOFactory Factory doesn't exist.");
            }
        } catch (Exception e){
            throw new DAOSystemException("AbstractDAOFactory exception. Error in creating of DAO factory", e);
        }
        return result;
    }

    private static synchronized void setType(DAOFactoryFactoryType daoFactoryFactoryType){
        currentType = daoFactoryFactoryType;
    }

}
