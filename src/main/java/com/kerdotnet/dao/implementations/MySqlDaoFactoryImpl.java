package com.kerdotnet.dao.implementations;

import com.kerdotnet.dao.DaoFactory;
import com.kerdotnet.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * implements connection to MySQL dataBase
 * Yevhen Ivanov, 2018-04-01
 */
public class MySqlDaoFactoryImpl implements DaoFactory {

    //JDBC Database Credentials
    private final String JDBC_USER = "ecproject";
    private final String JDBC_PASSWORD = "ecproject";

    //JDBC Driver Name and Database URL
    private String JDBC_URL = "jdbc:mysql://localhost:3306/conference_management";
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public MySqlDaoFactoryImpl() {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * return connection to DataBase
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public UserDao getUserDao(Connection connection) {
        return null;
    }

}
