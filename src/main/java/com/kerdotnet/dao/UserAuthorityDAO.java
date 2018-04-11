package com.kerdotnet.dao;

import com.kerdotnet.beans.UserAuthority;

import java.sql.Connection;
import java.util.List;

/**
 * User Authority DAO implementation
 * Yevhen Ivanov, 2018-04-10
 */

public class UserAuthorityDAO extends AbstractDAO<Integer, UserAuthority> {

    public static final String SQL_SELECT_ALL_USER_AUTHORITY =
            "SELECT * FROM user_authority";
    public static final String SQL_SELECT_USER_AUTHORITY_BY_ID =
            "SELECT * FROM user_authority WHERE id=?";
    public static final String SQL_SELECT_USER_AUTHORITY_BY_USER_ID =
            "SELECT * FROM user_authority WHERE user_id=?";
    public static final String SQL_INSERT_NEW_USER_AUTHORITY = "INSERT INTO user_authority  " +
            " (user_id, authority_id) VALUES " +
            " (?,?)";
    public static final String SQL_UPDATE_USER_AUTHORITY = "UPDATE user_authority SET " +
            " user_id = ?, authority_id = ? " +
            "WHERE id = ?";
    public static final String SQL_DELETE_USER_AUTHORITY = "DELETE FROM user_authority WHERE id = ?";

    public UserAuthorityDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<UserAuthority> findAll() {
        return null;
    }

    public List<UserAuthority> findAllByUserId() {
        return null;
    }

    public boolean updateAll(List<UserAuthority> authorities) {
        return false;
    }

    @Override
    public boolean delete(UserAuthority entity) {
        return false;
    }

    @Override
    public boolean create(UserAuthority entity) {
        return false;
    }

    @Override
    public UserAuthority update(UserAuthority entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserAuthority findEntity(Integer id) {
        throw new UnsupportedOperationException();
    }
}
