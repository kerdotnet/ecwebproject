package com.kerdotnet.dao.connectionfactory;

/**
 * this class implements connection daofactory daofactory
 * which create proper connection daofactory
 * YevhenIvanov; 2018-04-26
 */
public class ConnectionFactoryFactory {
    public enum ConnectionFactoryType {STANDARD}

    private static ConnectionFactoryType currentType = ConnectionFactoryType.STANDARD;

    private static synchronized void setType(ConnectionFactoryType factoryType) {
        currentType = factoryType;
    }

    public static synchronized ConnectionFactory newConnectionFactory() {
        ConnectionFactory result;

        switch (currentType) {
            case STANDARD:
                result = ConnectionFactoryJDBCStandard.getInstance();
                break;
            default:
                throw new RuntimeException("Connection Factory doesn't exist.");
        }

        return result;
    }
}
