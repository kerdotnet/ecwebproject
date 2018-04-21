package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.BookCatalogAuthor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BookCatalog extractor return BookCatalogAuthor from ResultSet
 * and create preparedStatements by Entity
 * Yevhen Ivanov, 2018-04-21
 */
public class BookCatalogAuthorExtractor extends Extractor<BookCatalogAuthor> {

    @Override
    public BookCatalogAuthor extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");

        int authorId = rs.getInt("author_id");
        int bookCatalogId = rs.getInt("bookcatalog_id");

        return new BookCatalogAuthor(id, authorId, bookCatalogId);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, BookCatalogAuthor entity) throws SQLException {
        preparedStatement.setInt(1, entity.getAuthorId());
        preparedStatement.setInt(2, entity.getBookCatalogId());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, BookCatalogAuthor entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(3, entity.getId());
    }
}
