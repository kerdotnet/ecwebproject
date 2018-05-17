package com.kerdotnet.dao.helpers;

import com.kerdotnet.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User extractor return User from ResultSet
 * Yevhen Ivanov, 2018-04-11
 */

public class UserExtractor extends Extractor<User> {

    @Override
    public User extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String mobile = rs.getString("mobile");
        boolean enabled = rs.getBoolean("flag_enabled");

        return new User(id, username, password, email,
                firstName, lastName, mobile, enabled);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedStatement.setString(1, entity.getUsername());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setString(3, entity.getEmail());
        preparedStatement.setString(4, entity.getFirstName());
        preparedStatement.setString(5, entity.getLastName());
        preparedStatement.setString(6, entity.getMobile());
        preparedStatement.setBoolean(7, entity.isEnabled());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, User entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(8, entity.getId());
    }
}
