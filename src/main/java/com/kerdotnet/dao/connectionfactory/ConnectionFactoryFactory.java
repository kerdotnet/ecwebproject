package com.kerdotnet.dao.connectionfactory;

import com.kerdotnet.exceptions.DAOSystemException;

/**
 * this class implements connection daofactory daofactory
 * which create proper connection daofactory
 * YevhenIvanov; 2018-04-26
 */
public class ConnectionFactoryFactory {
    public enum ConnectionFactoryType {STANDARD}
    private static ConnectionFactoryType currentType = ConnectionFactoryType.STANDARD;

    private static synchronized void setType(ConnectionFactoryType factoryType){
        currentType = factoryType;
    }

    public static synchronized ConnectionFactory newConnectioFactory() throws DAOSystemException {
        ConnectionFactory result;

        try {
            switch (currentType){
                case STANDARD:
                    result = ConnectionFactoryJDBCStandard.getInstance();
                    break;
                default:
                    throw new RuntimeException("Connection Factory doesn't exist.");
            }
        } catch (Exception e){
            throw new DAOSystemException("ConnectionFactoryFactory exception. Error in opening of ConnectionFactory", e);
        }

        return result;
    }
}
