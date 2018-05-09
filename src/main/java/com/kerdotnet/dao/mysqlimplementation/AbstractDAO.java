package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.Entity;
import com.kerdotnet.dao.connectionfactory.ConnectionFactory;
import com.kerdotnet.dao.connectionfactory.ConnectionFactoryFactory;
import com.kerdotnet.dao.connectionfactory.ConnectionWrapper;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.dao.helpers.Extractor;
import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for every IDAO implementation
 * with common methods for every IDAO
 * Yevhen Ivanov, 2018-04-07
 */
public abstract class AbstractDAO<T extends Entity> {
    protected ConnectionFactory connectionFactory;

    public AbstractDAO() {
        connectionFactory = ConnectionFactoryFactory.newConnectionFactory();
    }

    public AbstractDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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

    protected void close(ConnectionWrapper connection) throws DAOSystemException {
        try {
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e){
            throw new DAOSystemException("Error in close method() for result set of Abstract DAO system (MySQL impl)", e);
        }
    }
    public T findEntity(String sql, int key, Extractor<T> extractor,
                        IEnricher<T> enricher) throws DAOSystemException {
        return findEntityByObjectParameters(sql, extractor, enricher, key);
    }

    public T findEntityByStringParameter(String sql, String param, Extractor<T> extractor,
                                         IEnricher<T> enricher) throws DAOSystemException {
        return findEntityByObjectParameters(sql, extractor, enricher, param);
    }

    public T findEntityByObjectParameters(String sql, Extractor<T> extractor,
                                          IEnricher<T> enricher, Object ...params) throws DAOSystemException {
        T user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try (ConnectionWrapper connection = connectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 1; i <= params.length; i++){
                preparedStatement.setObject(i, params[i-1]);
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = extractor.extractOne(resultSet);
                enricher.enrich(user);
            }
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute findUserByStringParamenter method in MySQL DAOimpl", e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return user;
    }

    public boolean create(String sql, T entity,
                          Extractor<T> extractor) throws DAOSystemException {
        boolean flag = false;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try (ConnectionWrapper connection = connectionFactory.getConnection()) {
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
        try (ConnectionWrapper connection = connectionFactory.getConnection())  {
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
        try (ConnectionWrapper connection = connectionFactory.getConnection()) {
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

    public List<T> findAll(String sql, Extractor<T> extractor,
                           IEnricher<T> enricher) throws DAOSystemException {
        return findAllByObjectParameters(sql, extractor, enricher);
    }
    public List<T> findAllByInt(String sql, int param, Extractor<T> extractor,
                                IEnricher<T> enricher) throws DAOSystemException {
        return findAllByObjectParameters(sql, extractor, enricher, param);
    }

    public int findQuantityByObjectParameters(String sql, Object ...params) throws DAOSystemException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (ConnectionWrapper connection = connectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 1; i <= params.length; i++){
                preparedStatement.setObject(i, params[i-1]);
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("COUNT(*)");
            }
            return 0;
        } catch (SQLException e){
            throw new DAOSystemException("Can't execute findAll method in MySQL DAOimpl " + sql, e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
    }

    public List<T> findAllByObjectParameters(String sql, Extractor<T> extractor,
                                             IEnricher<T> enricher, Object... params) throws DAOSystemException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (ConnectionWrapper connection = connectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 1; i <= params.length; i++){
                preparedStatement.setObject(i, params[i-1]);
            }
            resultSet = preparedStatement.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()){
                T record = extractor.extractOne(resultSet);
                enricher.enrich(record);
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
