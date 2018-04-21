package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Transaction extractor return Transaction from ResultSet
 * and create preparedStatements by Entity
 * Yevhen Ivanov, 2018-04-21
 */
public class TransactionExtractor extends Extractor<Transaction> {

    @Override
    public Transaction extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");

        Timestamp timestamp = rs.getTimestamp("date");
        Date date = null;
        if (timestamp != null)
            date = new java.util.Date(timestamp.getTime());

        int bookItemId = rs.getInt("bookitem_id");
        int userId = rs.getInt("user_id");
        String bookShelfAddress = rs.getString("bookshelf_address");
        String action = rs.getString("action");

        boolean isEnabled = rs.getBoolean("flag_enabled");

        return new Transaction(id, date, bookItemId, userId, bookShelfAddress, action, isEnabled);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, Transaction entity) throws SQLException {
        preparedStatement.setDate(1, (java.sql.Date) entity.getDate());
        preparedStatement.setInt(2, entity.getBookItemId());
        preparedStatement.setInt(3, entity.getUserId());
        preparedStatement.setString(4, entity.getBookShelfAddress());
        preparedStatement.setString(5, entity.getAction());
        preparedStatement.setBoolean(6, entity.isEnabled());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, Transaction entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(7, entity.getId());
    }
}
