package com.kerdotnet.dao.SQLImplementation;

import com.kerdotnet.beans.User;
import com.kerdotnet.dao.UserDAO;
import com.kerdotnet.dao.helpers.UserEnricher;
import com.kerdotnet.dao.helpers.UserExtractor;
import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.*;
import java.util.List;

public class UserDAOImpl extends AbstractDAO implements UserDAO {

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM user";
    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM user WHERE id=?";
    public static final String SQL_SELECT_BY_USERNAME =
            "SELECT * FROM user WHERE username=?";
    public static final String SQL_INSERT_ONE = "INSERT INTO user  " +
            " (username, password, email, first_name, last_name, mobile, flag_enabled) VALUES " +
            " (?,?,?,?,?,?,?)";
    public static final String SQL_UPDATE_ONE = "UPDATE user SET " +
            " username = ?, password = ?, email = ?, first_name = ?, last_name = ?, mobile = ?, flag_enabled =? " +
            "WHERE id = ?";
    public static final String SQL_DELETE_ONE = "DELETE FROM user WHERE id = ?";

    public UserDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User findEntity(Integer id) throws DAOSystemException {
       return (User) findEntity(SQL_SELECT_BY_ID, id,  new UserExtractor(),
               new UserEnricher(new UserAuthorityDAOImpl(connection)));
    }

    @Override
    public List<User> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new UserExtractor(),
                new UserEnricher(new UserAuthorityDAOImpl(connection)));
    }

    @Override
    public boolean create(User entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new UserExtractor());
    }

    @Override
    public boolean update(User entity) throws DAOSystemException {
        return update(SQL_DELETE_ONE, entity, new UserExtractor());
    }

    @Override
    public boolean delete(User entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }

    @Override
    public User findUserByUserName(String userName) throws DAOSystemException {
        return (User) findUserByStringParameter(SQL_SELECT_BY_USERNAME, userName,  new UserExtractor(),
                new UserEnricher(new UserAuthorityDAOImpl(connection)));
    }
}
