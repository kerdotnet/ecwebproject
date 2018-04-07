package com.kerdotnet.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic DAO interface
 * Yevhen Ivanov, 2018-04-01
 * <T,K>
 *     where is
 *     T - entity
 *     K - key
 */

public interface GenericDao<T,Key> {

    /**
     * Create a recrd and return a new object T for it
     *
     */
    T create() throws SQLException;

    /**
     * Create a new record for (T) object and return it
     */
    T persist(T object)  throws SQLException;

    /**
     * find a record by primary key and return an (T) object for it
     */
    T getByKey(Key key) throws SQLException;

    /**
     * update an (T) object in DataBase
     */
    void update(T object) throws SQLException;

    /**
     * Delete an object in DataBase
     */
    void delete(T object) throws SQLException;

    /**
     * return all entities from DataBase as a list
     */
    List<T> getAll() throws SQLException;

}
