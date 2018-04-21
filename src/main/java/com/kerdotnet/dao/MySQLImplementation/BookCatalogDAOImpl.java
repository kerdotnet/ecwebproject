package com.kerdotnet.dao.MySQLImplementation;

import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.dao.IBookCatalogDAO;
import com.kerdotnet.dao.helpers.BookCatalogExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.Connection;
import java.util.List;

public class BookCatalogDAOImpl extends AbstractDAO implements IBookCatalogDAO {

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM bookcatalog";
    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM bookcatalog WHERE id=?";
    public static final String SQL_INSERT_ONE = "INSERT INTO bookcatalog  " +
            " (name, full_name, description, key_words, flag_enabled) VALUES " +
            " (?,?,?,?,?)";
    public static final String SQL_UPDATE_ONE = "UPDATE bookcatalog SET " +
            " name = ?, full_name = ?, description = ?, key_words = ?, flag_enabled = ? " +
            "WHERE id = ?";
    public static final String SQL_DELETE_ONE = "DELETE FROM bookcatalog WHERE id = ?";

    public BookCatalogDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public BookCatalog findEntity(Integer id) throws DAOSystemException {
        return (BookCatalog) findEntity(SQL_SELECT_BY_ID, id,  new BookCatalogExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<BookCatalog> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new BookCatalogExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(BookCatalog entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new BookCatalogExtractor());
    }

    @Override
    public boolean update(BookCatalog entity) throws DAOSystemException {
        return update(SQL_DELETE_ONE, entity, new BookCatalogExtractor());
    }

    @Override
    public boolean delete(BookCatalog entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }
}
