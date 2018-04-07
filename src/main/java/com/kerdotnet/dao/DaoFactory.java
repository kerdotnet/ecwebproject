package com.kerdotnet.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Dao Factory interface
 * Yevhen Ivanov, 2018-04-01
 */

public interface DaoFactory {
    /**
     * return connection to DataBase
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * return an Object UserDao
     * @param connection
     * @return
     */
    UserDao getUserDao(Connection connection);
}
