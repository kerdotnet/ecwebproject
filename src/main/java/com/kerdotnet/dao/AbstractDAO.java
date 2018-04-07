package com.kerdotnet.dao;

import com.kerdotnet.beans.Entity;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * The base class for every DAO implementation
 * with common methods for every DAO
 * Yevhen Ivanov, 2018-04-07
 */

public abstract class AbstractDAO<K, T extends Entity> {
    static final Logger LOGGER = Logger.getLogger(AbstractDAO.class);
    protected Connection connection;

    public abstract List<T> findAll();
    public abstract T findEntity(K id);
    public abstract boolean delete(T entity);
    public abstract boolean create(T entity);
    public abstract T update(T entity);

    public AbstractDAO(Connection connection){
        this.connection = connection;
    }

    public void close(Statement statement){
        try {
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
        }
    }

    public void close(Connection connection){
        try {
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
        }
    }
}
