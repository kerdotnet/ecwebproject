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

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM bookcatalog ORDER BY name";
    private static final String SQL_SELECT_ALL_BY_PAGE =
            "SELECT * FROM bookcatalog ORDER BY name, id LIMIT ?, ?";
    private static final String SQL_SELECT_ALL_QUANTITY =
            "SELECT COUNT(*) FROM bookcatalog ORDER BY name";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM bookcatalog WHERE id=?";
    private static final String SQL_INSERT_ONE = "INSERT INTO bookcatalog  " +
            " (name, full_name, description, key_words, flag_enabled) VALUES " +
            " (?,?,?,?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE bookcatalog SET " +
            " name = ?, full_name = ?, description = ?, key_words = ?, flag_enabled = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM bookcatalog WHERE id = ?";
    private static final String SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL =
            "SELECT * FROM bookcatalog WHERE key_words LIKE  ? " +
                    "OR full_name LIKE  ? " +
                    "OR description LIKE  ? " +
                    "OR id IN (select bookcatalog_author.bookcatalog_id from bookcatalog_author " +
                    "inner join author on bookcatalog_author.author_id = author.id " +
                    " where author.name LIKE  ? ) ORDER BY name";

    private static final String SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL_BY_PAGE =
            "SELECT * FROM bookcatalog WHERE key_words LIKE  ? " +
                    "OR full_name LIKE  ? " +
                    "OR description LIKE  ? " +
                    "OR id IN (select bookcatalog_author.bookcatalog_id from bookcatalog_author " +
                    "inner join author on bookcatalog_author.author_id = author.id " +
                    " where author.name LIKE  ? ) ORDER BY name LIMIT ?, ?";

    private static final String SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL_QUANTITY =
            "SELECT COUNT(*) FROM bookcatalog WHERE key_words LIKE  ? " +
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
    public List<BookCatalog> findByKeywordsOrNameOrAuthor(String searchRequest) throws DAOSystemException {
        String likeSearchParam = "%" + searchRequest + "%";
        return findAllByObjectParameters(SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL, new BookCatalogExtractor(),
                IEnricher.NULL, likeSearchParam, likeSearchParam, likeSearchParam, likeSearchParam);
    }

    @Override
    public int findQuantity() throws DAOSystemException {
        return findQuantityByObjectParameters(SQL_SELECT_ALL_QUANTITY);
    }

    @Override
    public int findQuantityByKeywordsOrNameOrAuthor(String searchRequest) throws DAOSystemException {
        String likeSearchParam = "%" + searchRequest + "%";
        return findQuantityByObjectParameters(SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL_QUANTITY,
                likeSearchParam, likeSearchParam, likeSearchParam, likeSearchParam);
    }

    @Override
    public List<BookCatalog> findAllByPage(int page, int quantityAtPage) throws DAOSystemException {
        return findAllByObjectParameters(SQL_SELECT_ALL_BY_PAGE, new BookCatalogExtractor(),
                IEnricher.NULL, page, quantityAtPage);
    }

    @Override
    public List<BookCatalog> findByKeywordsOrNameOrAuthorByPage(
            String searchRequest, int page, int quantityAtPage) throws DAOSystemException {
        String likeSearchParam = "%" + searchRequest + "%";
        return findAllByObjectParameters(SQL_SELECT_ALL_BY_SEARCH_REQUEST_FULL_BY_PAGE,
                new BookCatalogExtractor(), IEnricher.NULL,
                likeSearchParam, likeSearchParam, likeSearchParam, likeSearchParam, page, quantityAtPage);
    }
}
