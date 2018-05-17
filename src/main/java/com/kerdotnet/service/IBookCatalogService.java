package com.kerdotnet.service;

import com.kerdotnet.entity.Author;
import com.kerdotnet.entity.BookCatalog;
import com.kerdotnet.exceptions.ServiceException;

import java.util.List;

/**
 * Services related to BookCatalogs and authors
 * Yevhen Ivanov, 2018-05-09
 */
public interface IBookCatalogService  extends IService{

    /**
     * return a quantity of records in DB of BookCatalog
     * by the SearchRequest
     * @param searchRequest - specify the query to DB
     * @return
     * @throws ServiceException
     */
    int getAllBookCatalogBySearchRequestFullTextQuantity(
            String searchRequest) throws ServiceException;

    /**
     * Return a list of BookCatalog by searchRequest
     * by pages
     * @param searchRequest - specify the query to DB
     * @param page - the number of page
     * @param quantityAtPage - the quantity of elements at one page
     * @return
     * @throws ServiceException
     */
    List<BookCatalog> getAllBookCatalogBySearchRequestFullTextByPage(
            String searchRequest, int page, int quantityAtPage) throws ServiceException;

    int getAllBookCatalogQuantity() throws ServiceException;

    /**
     * get one BookCatalog Entity from DB by Id
     * @param id
     * @return
     * @throws ServiceException
     */
    BookCatalog getBookCatalogById(int id) throws ServiceException;

    /**
     * return all Book Catalog entities from DB by page
     * @param page
     * @param quantityAtPage
     * @return
     * @throws ServiceException
     */
    List<BookCatalog> getAllBookCatalogByPage(int page, int quantityAtPage) throws ServiceException;

    boolean deleteBookCatalogById(int bookCatalogId) throws ServiceException;

    /**
     * Save a new (or existing) book catalog entity to DB
     * @param bookCatalog
     * @return
     * @throws ServiceException
     */
    boolean saveBookCatalogEntity(BookCatalog bookCatalog) throws ServiceException;

    /**
     * get a list of all Authors from DataBase
     * @return
     * @throws ServiceException
     */
    List<Author> getAllAuthors() throws ServiceException;

    /**
     * find Author by Id
     * @param id
     * @return
     * @throws ServiceException
     */
    Author findAuthorById(int id) throws ServiceException;
}
