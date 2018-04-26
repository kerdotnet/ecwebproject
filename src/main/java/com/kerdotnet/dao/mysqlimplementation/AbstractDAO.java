package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.Entity;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.dao.helpers.Extractor;
import com.kerdotnet.exceptions.DAOSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for every IDAO implementation
 * with common methods for every IDAO
 * Yevhen Ivanov, 2018-04-07
 */

public abstract class AbstractDAO<T extends Entity> {
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
            throw new DAOSystemException("Error in close method() for statement of Abstract DAO system (MySQL impl)", e);
        }
    }

    protected void close(ResultSet resultSet) throws DAOSystemException {
        try {
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e){
            throw new DAOSystemException("Error in close method() for result set of Abstract DAO system (MySQL impl)", e);
        }
    }

    public T findEntity(String sql, int key, Extractor<T> extractor,
                        IEnricher<T> IEnricher) throws DAOSystemException {
        T record = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, key);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                record = extractor.extractOne(resultSet);
                IEnricher.enrich(record);
            }
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute findEntity method in MySQL DAOimpl", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return record;
    }

    public List<T> findAll(String sql, Extractor<T> extractor,
                           IEnricher<T> IEnricher) throws DAOSystemException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<T> result = new ArrayList<>();
            while (resultSet.next()){
                T record = extractor.extractOne(resultSet);
                IEnricher.enrich(record);
                result.add(record);
            }
            return result;
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute findAll method in MySQL DAOimpl " + sql, e);
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
                throw new DAOSystemException("Creating of Entity was failed, no ID obtained.");
            }
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute create method in MySQL DAOimpl", e);
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
            throw new DAOSystemException("Can't execute update method in MySQL DAOimpl", e);
        } finally {
            close(preparedStatement);
        }
        return flag;
    }

    public boolean delete(String sql, int key) throws DAOSystemException {
        boolean flag = false;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, key);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute delete method in MySQL DAOimpl", e);
        } finally {
            close(preparedStatement);
        }
        return flag;
    }

    public boolean delete(String sql, T entity) throws DAOSystemException {
        return delete(sql, entity.getId());
    }

    public T findUserByStringParameter(String sql, String param, Extractor<T> extractor,
                                       IEnricher<T> IEnricher) throws DAOSystemException {
        T user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, param);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = extractor.extractOne(resultSet);
                IEnricher.enrich(user);
            }
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute findUserByStringParamenter method in MySQL DAOimpl", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return user;
    }

    public List<T> findAllByInt(String sql, int param, Extractor<T> extractor,
                           IEnricher<T> IEnricher) throws DAOSystemException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, param);
            resultSet = preparedStatement.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()){
                T record = extractor.extractOne(resultSet);
                IEnricher.enrich(record);
                result.add(record);
            }
            return result;
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute findAllByInt method in MySQL DAOimpl " + sql, e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
    }

}
