package com.kerdotnet.dao.helpers;

import com.kerdotnet.entity.BookItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BookCatalog extractor return BookCatalog from ResultSet
 * and create preparedStatements by Entity
 * Yevhen Ivanov, 2018-04-21
 */
public class BookItemExtractor extends Extractor<BookItem> {

    @Override
    public BookItem extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");

        int bookCatalogId = rs.getInt("bookcatalog_id");
        String description = rs.getString("description");
        String bookShelfAddress = rs.getString("bookshelf_address");

        boolean isEnabled = rs.getBoolean("flag_enabled");

        return new BookItem(id, bookCatalogId, description, bookShelfAddress, isEnabled);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, BookItem entity) throws SQLException {
        preparedStatement.setInt(1, entity.getBookCatalogId());
        preparedStatement.setString(2, entity.getDescription());
        preparedStatement.setString(3, entity.getBookShelfAddress());

        preparedStatement.setBoolean(4, entity.isEnabled());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, BookItem entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(5, entity.getId());
    }
}
