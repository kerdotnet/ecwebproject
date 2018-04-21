package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.Authority;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Authority extractor
 * Yevhen Ivanov, 2018-04-21
 */

public class AuthorityExtractor extends Extractor<Authority> {

    @Override
    public Authority extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        boolean isUser = rs.getBoolean("flag_user");
        boolean isAdministrator = rs.getBoolean("flag_administrator");

        return new Authority(id, name, isUser, isAdministrator);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, Authority entity) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setBoolean(2, entity.isUser());
        preparedStatement.setBoolean(3, entity.isAdministrator());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, Authority entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(4, entity.getId());
    }
}
