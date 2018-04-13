package com.kerdotnet.dao;

/**
 * User DAO interface
 * Yevhen Ivanov, 2018-04-11
 */

import com.kerdotnet.beans.User;
import com.kerdotnet.exceptions.DAOSystemException;

public interface UserDAO extends DAO<Integer, User>{
    User findUserByUserName(String userName) throws DAOSystemException;
}
