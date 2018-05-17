package com.kerdotnet.dao.helpers;

import com.kerdotnet.entity.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author extractor return Author from ResultSet
 * and create preparedStatements by Entity
 * Yevhen Ivanov, 2018-04-21
 */
public class AuthorExtractor extends Extractor<Author> {

    @Override
    public Author extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        boolean isEnabled = rs.getBoolean("flag_enabled");

        return new Author(id, name, description, isEnabled);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, Author entity) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getDescritpion());
        preparedStatement.setBoolean(3, entity.isEnabled());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, Author entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(4, entity.getId());
    }
}
