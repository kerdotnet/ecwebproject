package com.kerdotnet.dao;

import com.kerdotnet.beans.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**User DAO implementation
 * Yevhen Ivanov, 2018-04-07
 */

public class UserDAO extends AbstractDAO<Integer, User> {

    public static final String SQL_SELECT_ALL_USERS =
            "SELECT * FROM user";
    public static final String SQL_SELECT_USER_BY_ID =
            "SELECT * FROM user WHERE id=?";
    public static final String SQL_SELECT_USER_BY_USERNAME =
            "SELECT * FROM user WHERE username=?";
    public static final String SQL_INSERT_NEW_USER = "INSERT INTO user  " +
            " (username, password, email, first_name, last_name, mobile, flag_enabled) VALUES " +
            " (?,?,?,?,?,?,?)";


    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()){
                User user = new User();
                fillUserByResultSet(resultSet, user);
                users.add(user);
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
        } finally {
            close(statement);
        }
        return users;
    }

    private void fillUserByResultSet(ResultSet resultSet, User user) throws SQLException {
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setMobile(resultSet.getString("mobile"));
        user.setEnabled(resultSet.getBoolean("flag_enabled"));
    }

    @Override
    public User findEntity(Integer id) {
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                fillUserByResultSet(resultSet, user);
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
        } finally {
            close(preparedStatement);
        }
        return user;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_USER);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getFirstName());
            preparedStatement.setString(5, entity.getLastName());
            preparedStatement.setString(6, entity.getMobile());
            preparedStatement.setBoolean(7, entity.isEnabled());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
        } finally {
            close(preparedStatement);
        }
        return true;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    public User findUserByUserName(String userName){
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                fillUserByResultSet(resultSet, user);
            }
        } catch (SQLException e){
            LOGGER.error("Unexpected error", e);
        } finally {
            close(preparedStatement);
        }
        return user;
    }
}
