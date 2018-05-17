package com.kerdotnet.dao;

import com.kerdotnet.entity.UserAuthority;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * User Authority IDAO interface
 * Yevhen Ivanov, 2018-04-11
 */

public interface IUserAuthorityDAO extends IDAO<Integer, UserAuthority> {
    List<UserAuthority> findAllByUserId(int userId) throws DAOSystemException;
}
