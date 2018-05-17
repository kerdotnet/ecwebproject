package com.kerdotnet.dao.helpers;

import com.kerdotnet.entity.BookCatalog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BookItem extractor return BookItem from ResultSet
 * and create preparedStatements by Entity
 * Yevhen Ivanov, 2018-04-21
 */
public class BookCatalogExtractor extends Extractor<BookCatalog> {

    @Override
    public BookCatalog extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");

        String name = rs.getString("name");
        String fullName = rs.getString("full_name");
        String description = rs.getString("description");
        String keyWords = rs.getString("key_words");

        boolean isEnabled = rs.getBoolean("flag_enabled");

        return new BookCatalog(id, name, fullName, description, keyWords, isEnabled);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, BookCatalog entity) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getFullName());
        preparedStatement.setString(3, entity.getDescription());
        preparedStatement.setString(4, entity.getKeywords());

        preparedStatement.setBoolean(5, entity.isEnabled());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, BookCatalog entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getId());
    }
}
