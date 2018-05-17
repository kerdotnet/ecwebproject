package com.kerdotnet.dao;

import com.kerdotnet.entity.Entity;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * Top common IDAO interface
 * Yevhen Ivanov, 2018-04-11
 */

public interface IDAO<K, T extends Entity>{
    T findEntity(K id) throws DAOSystemException ;
    List<T> findAll() throws DAOSystemException;
    boolean create(T entity) throws DAOSystemException ;
    boolean update(T entity) throws DAOSystemException ;
    boolean delete(T entity) throws DAOSystemException ;
}
