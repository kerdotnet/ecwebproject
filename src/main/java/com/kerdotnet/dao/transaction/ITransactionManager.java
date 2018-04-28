package com.kerdotnet.dao.transaction;

import java.util.concurrent.Callable;

/**
 * Interface aimed to implement transaction management
 * Yevhen Ivanov, 2018-04-27
 */
public interface ITransactionManager {
    <T> T doInTransaction(Callable<T> unitOfWork) throws Exception;
}
