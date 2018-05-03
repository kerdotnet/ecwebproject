package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.dao.helpers.BookItemEnricher;
import com.kerdotnet.dao.helpers.BookItemExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.Connection;
import java.util.List;

public class BookItemDAOImpl extends AbstractDAO implements IBookItemDAO {

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM bookitem";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM bookitem WHERE id=?";
    private static final String SQL_SELECT_BY_BOOKCATALOG_ID =
            "SELECT * FROM bookitem WHERE bookcatalog_id=?";
    private static final String SQL_SELECT_BY_BOOKCATALOG_ID_ON_SHELVES =
            "SELECT * FROM bookitem WHERE bookcatalog_id=? " +
                    "and id NOT IN " +
                    "(SELECT bookitem_id FROM bookitem_user WHERE flag_enabled)";
    private static final String SQL_SELECT_All_TAKEN_BY_USERS =
            "SELECT * FROM bookitem  " +
                    "WHERE id IN " +
                    "(SELECT bookitem_id FROM bookitem_user WHERE flag_enabled)";
    private static final String SQL_INSERT_ONE = "INSERT INTO bookitem  " +
            " (bookcatalog_id, description, bookshelf_address, flag_enabled) VALUES " +
            " (?,?,?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE bookitem SET " +
            " bookcatalog_id = ?, description = ?, bookshelf_address = ?, flag_enabled = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM bookitem WHERE id = ?";

    public BookItemDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public BookItem findEntity(Integer id) throws DAOSystemException {
        return (BookItem) findEntity(SQL_SELECT_BY_ID, id,  new BookItemExtractor(),
                new BookItemEnricher(new BookItemUserDAOImpl(connection)));
    }

    @Override
    public List<BookItem> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new BookItemExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(BookItem entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new BookItemExtractor());
    }

    @Override
    public boolean update(BookItem entity) throws DAOSystemException {
        return update(SQL_UPDATE_ONE, entity, new BookItemExtractor());
    }

    @Override
    public boolean delete(BookItem entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }

    @Override
    public List<BookItem> findByBookCatalogId(int bookCatalogId) throws DAOSystemException {
        return findAllByInt(SQL_SELECT_BY_BOOKCATALOG_ID, bookCatalogId,  new BookItemExtractor(),
                new BookItemEnricher(new BookItemUserDAOImpl(connection)));
    }

    @Override
    public List<BookItem> findByBookCatalogIdOnShelves(int bookCatalogId) throws DAOSystemException {
        return findAllByInt(SQL_SELECT_BY_BOOKCATALOG_ID_ON_SHELVES, bookCatalogId,  new BookItemExtractor(),
                new BookItemEnricher(new BookItemUserDAOImpl(connection)));
    }

    @Override
    public List<BookItem> findAllBookItemsTakenByUsers() throws DAOSystemException {
        return findAll(SQL_SELECT_All_TAKEN_BY_USERS, new BookItemExtractor(),
                new BookItemEnricher(new BookItemUserDAOImpl(connection)));
    }
}
