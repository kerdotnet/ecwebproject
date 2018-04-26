package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.beans.Transaction;
import com.kerdotnet.dao.ITransactionDAO;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.dao.helpers.TransactionExtractor;
import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.Connection;
import java.util.List;

public class TransactionDAOImpl extends AbstractDAO implements ITransactionDAO {

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM transaction";
    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM transaction WHERE id=?";
    public static final String SQL_INSERT_ONE = "INSERT INTO transaction  " +
            " (date, bookitem_id, user_id, bookshelf_address, action, flag_enabled) VALUES " +
            " (?,?,?,?,?,?)";
    public static final String SQL_UPDATE_ONE = "UPDATE transaction SET " +
            " date = ?, bookitem_id = ?, user_id = ?, bookshelf_address = ?, action = ?, flag_enabled = ? " +
            "WHERE id = ?";
    public static final String SQL_DELETE_ONE = "DELETE FROM transaction WHERE id = ?";

    public TransactionDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Transaction findEntity(Integer id) throws DAOSystemException {
        return (Transaction) findEntity(SQL_SELECT_BY_ID, id,  new TransactionExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<Transaction> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new TransactionExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(Transaction entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new TransactionExtractor());
    }

    @Override
    public boolean update(Transaction entity) throws DAOSystemException {
        return update(SQL_DELETE_ONE, entity, new TransactionExtractor());
    }

    @Override
    public boolean delete(Transaction entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }
}
