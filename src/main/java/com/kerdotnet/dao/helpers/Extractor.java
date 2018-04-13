package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class abstract Extractor for manipulation with result sets
 * Yevhen Ivanov, 2018-04-11
 */

public abstract class Extractor<T extends Entity> {
    public abstract T extractOne(ResultSet rs) throws SQLException;
    public abstract void setOne(PreparedStatement preparedStatement, T entity) throws SQLException;

    public List<T> extractAll(ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<>();

        while (rs.next()) {
            result.add(extractOne(rs));
        }
        return result;
    }
}
