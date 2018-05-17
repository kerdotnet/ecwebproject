package com.kerdotnet.dao;

import com.kerdotnet.entity.BookItemUser;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * BookItem-User relations IDAO interface
 * Yevhen Ivanov, 2018-04-21
 */
public interface IBookItemUserDAO   extends IDAO<Integer, BookItemUser>{
    List<BookItemUser> findAllByUserId(int userId) throws DAOSystemException;
    BookItemUser findActiveEntityByBookItemId(int bookItemId) throws DAOSystemException;

    List<BookItemUser>  findAllByBookItemId(int bookItemId) throws DAOSystemException;
}
