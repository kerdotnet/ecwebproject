package com.kerdotnet.dao.SQLImplementation;

import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.dao.UserAuthorityDAO;

import java.sql.Connection;
import java.util.List;

/**
 * User Authority DAO implementation
 * Yevhen Ivanov, 2018-04-10
 */

public class UserAuthorityDAOImpl extends AbstractDAO implements UserAuthorityDAO {

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM user_authority";
    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM user_authority WHERE id=?";
    public static final String SQL_SELECT_BY_USER_ID =
            "SELECT * FROM user_authority WHERE user_id=?";
    public static final String SQL_INSERT_ONE = "INSERT INTO user_authority  " +
            " (user_id, authority_id) VALUES " +
            " (?,?)";
    public static final String SQL_UPDATE_ONE = "UPDATE user_authority SET " +
            " user_id = ?, authority_id = ? " +
            "WHERE id = ?";
    public static final String SQL_DELETE_ONE = "DELETE FROM user_authority WHERE id = ?";

    public UserAuthorityDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<UserAuthority> findAll() {
        return null;
    }

    public List<UserAuthority> findAllByUserId(int userId) {
        return null;
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
    public boolean update(UserAuthority entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserAuthority findEntity(Integer id) {
        throw new UnsupportedOperationException();
    }
}
