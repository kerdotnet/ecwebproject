package com.kerdotnet.dao;

import com.kerdotnet.entity.BookCatalog;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

/**
 * BookCatalog IDAO interface
 * Yevhen Ivanov, 2018-04-21
 */
public interface IBookCatalogDAO extends IDAO<Integer, BookCatalog> {
    int findQuantity() throws DAOSystemException;
    int findQuantityByKeywordsOrNameOrAuthor(String searchRequest) throws DAOSystemException;
    List<BookCatalog> findByKeywordsOrNameOrAuthor(String searchRequest) throws DAOSystemException;
    List<BookCatalog> findAllByPage(int page, int quantityAtPage) throws DAOSystemException;
    List<BookCatalog> findByKeywordsOrNameOrAuthorByPage(
            String searchRequest, int page, int quantityAtPage) throws DAOSystemException;
}
