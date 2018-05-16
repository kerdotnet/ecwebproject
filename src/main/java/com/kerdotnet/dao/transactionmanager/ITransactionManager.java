package com.kerdotnet.dao.transactionmanager;

import java.util.concurrent.Callable;

/**
 * Interface aimed to implement transactionmanager management
 * Yevhen Ivanov, 2018-04-27
 */
public interface ITransactionManager {
    <T> T doInTransaction(Callable<T> unitOfWork) throws Exception;

    <T> T doWithoutTransaction(Callable<T> unitOfWork) throws Exception;
}
