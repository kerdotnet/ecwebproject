package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.dao.helpers.UserAuthorityExtractor;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

public class UserAuthorityDAOImpl extends AbstractDAO implements IUserAuthorityDAO {

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM user_authority";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM user_authority WHERE id=?";
    private static final String SQL_SELECT_BY_USER_ID =
            "SELECT * FROM user_authority WHERE user_id=?";
    private static final String SQL_INSERT_ONE = "INSERT INTO user_authority  " +
            " (user_id, authority_id) VALUES " +
            " (?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE user_authority SET " +
            " user_id = ?, authority_id = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM user_authority WHERE id = ?";

    public UserAuthorityDAOImpl() {
        super();
    }

    @Override
    public UserAuthority findEntity(Integer id) throws DAOSystemException {
        return (UserAuthority) findEntity(SQL_SELECT_BY_ID, id,  new UserAuthorityExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<UserAuthority> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new UserAuthorityExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(UserAuthority entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new UserAuthorityExtractor());
    }

    @Override
    public boolean update(UserAuthority entity) throws DAOSystemException {
        return update(SQL_UPDATE_ONE, entity, new UserAuthorityExtractor());
    }

    @Override
    public boolean delete(UserAuthority entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }

    public List<UserAuthority> findAllByUserId(int userId) throws DAOSystemException {
        return findAllByInt(SQL_SELECT_BY_USER_ID, userId,  new UserAuthorityExtractor(),
                IEnricher.NULL);
    }
}
