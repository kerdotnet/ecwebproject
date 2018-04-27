package com.kerdotnet.dao.connectionfactory;

import com.kerdotnet.exceptions.DAOSystemException;

import java.sql.Connection;

/**
 * Factory which generates connections
 * Yevhen Ivanov, 2018-04-26
 */
public interface ConnectionFactory {
    Connection getConnection() throws DAOSystemException;
}
