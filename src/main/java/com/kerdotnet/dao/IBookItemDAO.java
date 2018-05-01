package com.kerdotnet.dao;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * BookItem IDAO interface
 * Yevhen Ivanov, 2018-04-21
 */
public interface IBookItemDAO extends IDAO<Integer, BookItem> {
    List<BookItem> findByBookCatalogId(int bookCatalogId) throws DAOSystemException;
    List<BookItem> findByBookCatalogIdOnShelves(int bookCatalogId) throws DAOSystemException;

    List<BookItem> findAllBookItemsTakenByUsers() throws DAOSystemException;
}
