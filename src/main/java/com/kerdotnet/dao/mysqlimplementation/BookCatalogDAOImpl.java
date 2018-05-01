package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.dao.IBookCatalogDAO;
import com.kerdotnet.dao.helpers.BookCatalogExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class BookCatalogDAOImpl extends AbstractDAO implements IBookCatalogDAO {
    static final Logger LOGGER = LoggerFactory.getLogger(BookCatalogDAOImpl.class);

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
    private static final String SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL =
            "SELECT * FROM bookcatalog WHERE key_words LIKE  ? " +
                    "OR full_name LIKE  ? " +
                    "OR description LIKE  ? " +
                    "OR id IN (select bookcatalog_author.bookcatalog_id from bookcatalog_author " +
                    "inner join author on bookcatalog_author.author_id = author.id " +
                    " where author.name LIKE  ? )";

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
        return update(SQL_UPDATE_ONE, entity, new BookCatalogExtractor());
    }

    @Override
    public boolean delete(BookCatalog entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }

    @Override
    public List<BookCatalog> findByKeywords(String keywords) throws DAOSystemException {
        return null;
    }

    @Override
    public List<BookCatalog> findByNameFragment(String nameFragment) throws DAOSystemException {
        return null;
    }

    @Override
    public List<BookCatalog> findByAuthor(String author) throws DAOSystemException {
        return null;
    }

    @Override
    public List<BookCatalog> findByKeywordsOrNameOrAuthor(String searchRequest) throws DAOSystemException {
        LOGGER.debug(searchRequest);
        LOGGER.debug(SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL);
        String likeSearchParam = "%" + searchRequest + "%";
        return findAllByStrings(SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL, new BookCatalogExtractor(),
                IEnricher.NULL, likeSearchParam, likeSearchParam, likeSearchParam, likeSearchParam);
    }
}
