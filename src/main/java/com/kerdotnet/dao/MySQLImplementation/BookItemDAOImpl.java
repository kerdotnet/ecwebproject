package com.kerdotnet.dao.MySQLImplementation;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.dao.helpers.BookItemExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.Connection;
import java.util.List;

public class BookItemDAOImpl extends AbstractDAO implements IBookItemDAO {

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM bookitem";
    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM bookitem WHERE id=?";
    public static final String SQL_INSERT_ONE = "INSERT INTO bookitem  " +
            " (bookcatalog_id, description, bookshelf_address, flag_enabled) VALUES " +
            " (?,?,?,?)";
    public static final String SQL_UPDATE_ONE = "UPDATE bookitem SET " +
            " bookcatalog_id = ?, description = ?, bookshelf_address = ?, flag_enabled = ? " +
            "WHERE id = ?";
    public static final String SQL_DELETE_ONE = "DELETE FROM bookitem WHERE id = ?";

    public BookItemDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public BookItem findEntity(Integer id) throws DAOSystemException {
        return (BookItem) findEntity(SQL_SELECT_BY_ID, id,  new BookItemExtractor(),
                IEnricher.NULL);
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
        return update(SQL_DELETE_ONE, entity, new BookItemExtractor());
    }

    @Override
    public boolean delete(BookItem entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }
}
