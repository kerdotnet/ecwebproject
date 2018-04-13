package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.UserAuthority;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User extractor return User from ResultSet
 * Yevhen Ivanov, 2018-04-11
 */

public class UserAuthorityExtractor extends Extractor<UserAuthority> {

    @Override
    public UserAuthority extractOne(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        int authorityId = rs.getInt("authority_id");

        return new UserAuthority(id, userId, authorityId);
    }

    @Override
    public void setOneCreate(PreparedStatement preparedStatement, UserAuthority entity) throws SQLException {
        preparedStatement.setInt(1, entity.getUserId());
        preparedStatement.setInt(2, entity.getAuthorityId());
    }

    @Override
    public void setOneUpdate(PreparedStatement preparedStatement, UserAuthority entity) throws SQLException{
        setOneCreate(preparedStatement, entity);
        preparedStatement.setInt(3, entity.getId());
    }
}
