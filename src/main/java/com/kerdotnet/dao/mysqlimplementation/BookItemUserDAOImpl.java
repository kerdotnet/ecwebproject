package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.helpers.BookItemUserExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.Connection;
import java.util.List;

public class BookItemUserDAOImpl extends AbstractDAO implements IBookItemUserDAO {

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM bookitem_user";
    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM bookitem_user WHERE id=?";
    public static final String SQL_INSERT_ONE = "INSERT INTO bookitem_user  " +
            " (bookitem_id, user_id, date, flag_enabled) VALUES " +
            " (?,?,?,?)";
    public static final String SQL_UPDATE_ONE = "UPDATE bookitem_user SET " +
            " bookitem_id = ?, user_id = ?, date = ?, flag_enabled = ? " +
            "WHERE id = ?";
    public static final String SQL_DELETE_ONE = "DELETE FROM bookitem_user WHERE id = ?";

    public static final String SQL_SELECT_BY_USER_ID =
            "SELECT * FROM bookitem_user WHERE user_id=?";

    public BookItemUserDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public BookItemUser findEntity(Integer id) throws DAOSystemException {
        return (BookItemUser) findEntity(SQL_SELECT_BY_ID, id,  new BookItemUserExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<BookItemUser> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new BookItemUserExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(BookItemUser entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new BookItemUserExtractor());
    }

    @Override
    public boolean update(BookItemUser entity) throws DAOSystemException {
        return update(SQL_UPDATE_ONE, entity, new BookItemUserExtractor());
    }

    @Override
    public boolean delete(BookItemUser entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }

    @Override
    public List<BookItemUser> findAllByUserId(int userId) throws DAOSystemException {
        return findAllByInt(SQL_SELECT_BY_USER_ID, userId,  new BookItemUserExtractor(),
                IEnricher.NULL);
    }
}
