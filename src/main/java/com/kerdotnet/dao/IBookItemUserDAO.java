package com.kerdotnet.dao;

import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * BookItem-User relations IDAO interface
 * Yevhen Ivanov, 2018-04-21
 */
public interface IBookItemUserDAO   extends IDAO<Integer, BookItemUser>{
    List<BookItemUser> findAllByUserId(int userId) throws DAOSystemException;
}
