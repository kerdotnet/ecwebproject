package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.entity.BookItemUser;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.helpers.BookItemUserEnricher;
import com.kerdotnet.dao.helpers.BookItemUserExtractor;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

public class BookItemUserDAOImpl extends AbstractDAO implements IBookItemUserDAO {

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM bookitem_user where flag_enabled";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM bookitem_user WHERE id=?";
    private static final String SQL_SELECT_ALL_BY_BOOKITEM_ID =
            "SELECT * FROM bookitem_user WHERE bookitem_id=?";
    private static final String SQL_SELECT_ACTIVE_BY_BOOKITEM_ID =
            "SELECT * FROM bookitem_user WHERE bookitem_id=? and flag_enabled";
    private static final String SQL_INSERT_ONE = "INSERT INTO bookitem_user  " +
            " (bookitem_id, user_id, date, flag_enabled) VALUES " +
            " (?,?,?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE bookitem_user SET " +
            " bookitem_id = ?, user_id = ?, date = ?, flag_enabled = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM bookitem_user WHERE id = ?";

    private static final String SQL_SELECT_BY_USER_ID =
            "SELECT * FROM bookitem_user WHERE user_id=? and flag_enabled";

    public BookItemUserDAOImpl() {
        super();
    }

    @Override
    public BookItemUser findEntity(Integer id) throws DAOSystemException {
        return (BookItemUser) findEntity(SQL_SELECT_BY_ID, id,  new BookItemUserExtractor(),
                new BookItemUserEnricher(new UserDAOImpl()));
    }

    @Override
    public BookItemUser findActiveEntityByBookItemId(int bookItemId) throws DAOSystemException {
        return (BookItemUser) findEntity(SQL_SELECT_ACTIVE_BY_BOOKITEM_ID, bookItemId,  new BookItemUserExtractor(),
                new BookItemUserEnricher(new UserDAOImpl()));
    }

    @Override
    public List<BookItemUser> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new BookItemUserExtractor(),
                new BookItemUserEnricher(new UserDAOImpl()));
    }
    @Override
    public List<BookItemUser> findAllByBookItemId(int bookItemId) throws DAOSystemException {
        return findAllByInt(SQL_SELECT_ALL_BY_BOOKITEM_ID, bookItemId , new BookItemUserExtractor(),
                new BookItemUserEnricher(new UserDAOImpl()));
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
                new BookItemUserEnricher(new UserDAOImpl()));
    }
}
