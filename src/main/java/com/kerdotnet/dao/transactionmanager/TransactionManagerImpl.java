package com.kerdotnet.dao.transactionmanager;

import com.kerdotnet.dao.connectionfactory.ConnectionFactory;
import com.kerdotnet.dao.connectionfactory.ConnectionFactoryFactory;
import com.kerdotnet.dao.connectionfactory.ConnectionWrapper;

import java.sql.Connection;
import java.util.concurrent.Callable;

public class TransactionManagerImpl implements ITransactionManager{

    private static ThreadLocal<ConnectionWrapper> connectionHolder = new ThreadLocal<>();
    private static final ConnectionFactory factory = ConnectionFactoryFactory.newConnectionFactory();

    @Override
    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception {
        ConnectionWrapper connection = factory.getConnection();
        connectionHolder.set(connection);
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            T result = unitOfWork.call();
            connection.commit();
            return result;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connectionHolder.remove();
            factory.closeConnection();
        }
    }
}
