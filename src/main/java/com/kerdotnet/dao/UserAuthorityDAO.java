package com.kerdotnet.dao;

import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * User Authority DAO interface
 * Yevhen Ivanov, 2018-04-11
 */

public interface UserAuthorityDAO extends DAO<Integer, UserAuthority> {
    List<UserAuthority> findAllByUserId(int userId) throws DAOSystemException;
}
