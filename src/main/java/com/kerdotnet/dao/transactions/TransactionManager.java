package com.kerdotnet.dao.transactions;

import java.util.concurrent.Callable;

/**
 * interface for transactions management
 * Yevhen Ivanov, 2018-04-02
 */

public interface TransactionManager {

    /**
     * do in Transaction unit of work. If successfully, return T object,
     * else throws an exception and rollback transaction
     * @param unitOfWork
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T doInTransaction(Callable<T> unitOfWork)
            throws Exception;

}
