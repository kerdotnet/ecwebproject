package com.kerdotnet.dao;

import com.kerdotnet.beans.BookCatalogAuthor;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * BookCatalogAuthor IDAO interface
 * Yevhen Ivanov, 2018-04-21
 */
public interface IBookCatalogAuthorDAO extends IDAO<Integer, BookCatalogAuthor> {
    List<BookCatalogAuthor> findAllByBookCatalogId(int bookCatalogId) throws DAOSystemException;
    List<BookCatalogAuthor> findAllByAuthorId(int authorId) throws DAOSystemException;
    boolean deleteByBookCatalogId(int bookCatalogId) throws DAOSystemException ;
}
