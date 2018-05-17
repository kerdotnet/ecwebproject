package com.kerdotnet.dao;

/**
 * User IDAO interface
 * Yevhen Ivanov, 2018-04-11
 */

import com.kerdotnet.entity.User;
import com.kerdotnet.exceptions.DAOSystemException;

public interface IUserDAO extends IDAO<Integer, User> {
    User findUserByUserName(String userName) throws DAOSystemException;
    User findUserByEmail(String email) throws DAOSystemException;
}
