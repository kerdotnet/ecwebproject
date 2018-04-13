package com.kerdotnet.dao.SQLImplementation;

import com.kerdotnet.beans.Entity;
import com.kerdotnet.dao.helpers.Enricher;
import com.kerdotnet.dao.helpers.Extractor;
import com.kerdotnet.exceptions.DAOSystemException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for every DAO implementation
 * with common methods for every DAO
 * Yevhen Ivanov, 2018-04-07
 */

public abstract class AbstractDAO<T extends Entity> {
    static final Logger LOGGER = Logger.getLogger(AbstractDAO.class);
    protected Connection connection;

    public AbstractDAO(Connection connection){
        this.connection = connection;
    }

    protected void close(Statement statement) throws DAOSystemException {
        try {
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error in closing of Statement", e);
            throw new DAOSystemException("Error in close method() of DAO system", e);
        }
    }

    protected void close(ResultSet resultSet) throws DAOSystemException {
        try {
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error in closing of Result Set", e);
            throw new DAOSystemException("Error in close method() of DAO system", e);
        }
    }

    public T findEntity(String sql, int key, Extractor<T> extractor,
                        Enricher<T> enricher) throws DAOSystemException {
        T record = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, key);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                record = extractor.extractOne(resultSet);
                enricher.enrich(record);
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("Can't execute findEntity method in UserDAOimpl", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return record;
    }

    public List<T> findAll(String sql, Extractor<T> extractor,
                           Enricher<T> enricher) throws DAOSystemException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<T> result = new ArrayList<>();
            while (resultSet.next()){
                T record = extractor.extractOne(resultSet);
                enricher.enrich(record);
                result.add(record);
            }
            return result;
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("Can't execute findAll method in UserDAOimpl " + sql, e);
        } finally {
            close(resultSet);
            close(statement);
        }
    }

    public boolean create(String sql, T entity,
                          Extractor<T> extractor) throws DAOSystemException {
        boolean flag = false;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            extractor.setOneCreate(preparedStatement, entity);
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getInt(1));
                flag = true;
            }
            else {
                LOGGER.error("Creating user failed, no ID obtained.");
                throw new DAOSystemException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("Can't execute create method in UserDAOimpl", e);
        } finally {
            close(generatedKeys);
            close(preparedStatement);
        }
        return flag;
    }

    public boolean update(String sql, T entity,
                    Extractor<T> extractor) throws DAOSystemException {
        boolean flag = false;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            extractor.setOneUpdate(preparedStatement, entity);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("Can't execute update method in UserDAOimpl", e);
        } finally {
            close(preparedStatement);
        }
        return flag;
    }

    public boolean delete(String sql, T entity) throws DAOSystemException {
        boolean flag = false;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("Can't execute delete method in UserDAOimpl", e);
        } finally {
            close(preparedStatement);
        }
        return flag;
    }

    public T findUserByStringParameter(String sql, String param, Extractor<T> extractor,
                                       Enricher<T> enricher) throws DAOSystemException {
        T user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, param);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = extractor.extractOne(resultSet);
                enricher.enrich(user);
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("Can't execute findUserByUserName method in UserDAOimpl", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return user;
    }

    public List<T> findAllByInt(String sql, int param, Extractor<T> extractor,
                           Enricher<T> enricher) throws DAOSystemException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, param);
            resultSet = preparedStatement.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()){
                T record = extractor.extractOne(resultSet);
                enricher.enrich(record);
                result.add(record);
            }
            return result;
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
            throw new DAOSystemException("Can't execute findAll method in UserDAOimpl " + sql, e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
    }

}
