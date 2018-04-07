package com.kerdotnet.dao;

import com.kerdotnet.beans.User;

import java.sql.Connection;
import java.util.List;

/**User DAO implementation
 * Yevhen Ivanov, 2018-04-07
 */

public class UserDAO extends AbstractDAO<Integer, User> {

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findEntity(Integer id) {
        return null;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }
}
