package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.BookItemUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * BookItemUser extractor return BookItemUser entity from ResultSet
 * and create preparedStatements by Entity
 * Yevhen Ivanov, 2018-04-21
 */
public class BookItemUserExtractor extends Extractor<BookItemUser> {

    @Override
    public BookItemUser extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");

        int bookItemId = rs.getInt("bookitem_id");
        int userId = rs.getInt("user_id");

        Timestamp timestamp = rs.getTimestamp("date");
        Date date = null;
        if (timestamp != null)
            date = new java.util.Date(timestamp.getTime());

        boolean enabled = rs.getBoolean("flag_enabled");

        return new BookItemUser(id, bookItemId, userId, date, enabled);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, BookItemUser entity) throws SQLException {
        preparedStatement.setInt(1, entity.getBookItemId());
        preparedStatement.setInt(2, entity.getUserId());
        preparedStatement.setDate(3, (java.sql.Date) entity.getDate());
        preparedStatement.setBoolean(4, entity.isEnabled());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, BookItemUser entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(5, entity.getId());
    }
}
