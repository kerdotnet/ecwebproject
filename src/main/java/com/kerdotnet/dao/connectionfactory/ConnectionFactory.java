package com.kerdotnet.dao.connectionfactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Factory which generates connections
 * Yevhen Ivanov, 2018-04-26
 */
public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
