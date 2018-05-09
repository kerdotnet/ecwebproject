package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.Author;
import com.kerdotnet.dao.IAuthorDAO;
import com.kerdotnet.dao.helpers.AuthorExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

public class AuthorDAOImpl extends AbstractDAO implements IAuthorDAO {

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM author";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM author WHERE id=?";
    private static final String SQL_INSERT_ONE = "INSERT INTO author  " +
            " (name, description, flag_enabled) VALUES " +
            " (?,?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE author SET " +
            " name = ?, description = ?, flag_enabled = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM author WHERE id = ?";

    public AuthorDAOImpl() {
        super();
    }

    @Override
    public Author findEntity(Integer id) throws DAOSystemException {
        return (Author) findEntity(SQL_SELECT_BY_ID, id,  new AuthorExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<Author> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new AuthorExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(Author entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new AuthorExtractor());
    }

    @Override
    public boolean update(Author entity) throws DAOSystemException {
        return update(SQL_UPDATE_ONE, entity, new AuthorExtractor());
    }

    @Override
    public boolean delete(Author entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }
}
