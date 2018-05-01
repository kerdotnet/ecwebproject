package com.kerdotnet.dao;

import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * BookCatalog IDAO interface
 * Yevhen Ivanov, 2018-04-21
 */
public interface IBookCatalogDAO extends IDAO<Integer, BookCatalog> {
    List<BookCatalog> findByKeywords(String keywords) throws DAOSystemException;
    List<BookCatalog> findByNameFragment(String nameFragment) throws DAOSystemException;
    List<BookCatalog> findByAuthor(String author) throws DAOSystemException;
    List<BookCatalog> findByKeywordsOrNameOrAuthor(String searchRequest) throws DAOSystemException;
}
