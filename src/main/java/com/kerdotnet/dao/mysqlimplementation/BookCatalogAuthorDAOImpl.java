package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.entity.BookCatalogAuthor;
import com.kerdotnet.dao.IBookCatalogAuthorDAO;
import com.kerdotnet.dao.helpers.BookCatalogAuthorExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

public class BookCatalogAuthorDAOImpl extends AbstractDAO implements IBookCatalogAuthorDAO {

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM bookcatalog_author ORDER BY name";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM bookcatalog_author WHERE id=?";
    private static final String SQL_INSERT_ONE = "INSERT INTO bookcatalog_author  " +
            " (author_id, bookcatalog_id) VALUES " +
            " (?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE bookcatalog_author SET " +
            " author_id = ?, bookcatalog_id = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM bookcatalog_author WHERE id = ?";

    private static final String SQL_SELECT_BY_BOOKCATALOG_ID =
            "SELECT * FROM bookcatalog_author WHERE bookcatalog_id=?";

    private static final String SQL_SELECT_BY_AUTHOR_ID =
            "SELECT * FROM bookcatalog_author WHERE author_id=?";

    private static final String SQL_DELETE_BY_BOOK_CATALOG_ID =
            "DELETE FROM bookcatalog_author WHERE bookcatalog_id = ?";

    public BookCatalogAuthorDAOImpl() {
        super();
    }

    @Override
    public BookCatalogAuthor findEntity(Integer id) throws DAOSystemException {
        return (BookCatalogAuthor) findEntity(SQL_SELECT_BY_ID, id,  new BookCatalogAuthorExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<BookCatalogAuthor> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new BookCatalogAuthorExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(BookCatalogAuthor entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new BookCatalogAuthorExtractor());
    }

    @Override
    public boolean update(BookCatalogAuthor entity) throws DAOSystemException {
        return update(SQL_UPDATE_ONE, entity, new BookCatalogAuthorExtractor());
    }

    @Override
    public boolean delete(BookCatalogAuthor entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }

    @Override
    public boolean deleteByBookCatalogId(int bookCatalogId) throws DAOSystemException {
        return delete(SQL_DELETE_BY_BOOK_CATALOG_ID, bookCatalogId);
    }

    @Override
    public List<BookCatalogAuthor> findAllByBookCatalogId(int bookCatalogId) throws DAOSystemException {
        return findAllByInt(SQL_SELECT_BY_BOOKCATALOG_ID, bookCatalogId,  new BookCatalogAuthorExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<BookCatalogAuthor> findAllByAuthorId(int authorId) throws DAOSystemException {
        return findAllByInt(SQL_SELECT_BY_AUTHOR_ID, authorId,  new BookCatalogAuthorExtractor(),
                IEnricher.NULL);
    }
}
