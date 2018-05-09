package com.kerdotnet.service;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.dao.daofactory.IDAOFactory;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;

import java.util.List;

/** define BookItem service Logic
 * Yevhen Ivanov, 2018-05-09
 */
public interface IBookItemService extends IService {

    /**
     * Return a list fo book items which are in shelves (but not at users)
     * @param bookCatalogId
     * @return
     * @throws ServiceException
     */
    List<BookItem> getBookItemsByBookCatalogIdOnShelves(int bookCatalogId) throws ServiceException;

    /**
     * return a list of overdue bookitems
     * overdue means that user took earlier than OVERDUE_DURATION_IN_MONTHS
     * @return
     * @throws ServiceException
     */
    List<BookItem> getOverdueBookItemsTakenByUsers() throws ServiceException;

    /**
     * return a list of book items which were taken and not returned
     * by concrete user
     * @param login
     * @return
     * @throws ServiceException
     */
    List<BookItem> getAllBookItemsTakenByConcreteUser(String login) throws ServiceException;

    /**
     * return a list of book items which were taken and not returned
     * by any user (not on shelves)
     * @return
     * @throws ServiceException
     */
    List<BookItem> getAllBookItemsTakenByUsers() throws ServiceException;

    /**
     * sace changes in DB for the book item
     * @param bookItem
     * @return
     * @throws ServiceException
     */
    boolean saveBookItemEntity(BookItem bookItem) throws ServiceException;

    /**
     * delete a specific book item by Id from DB
     * @param bookItemId
     * @return
     * @throws ServiceException
     */
    boolean deleteBookItemById(int bookItemId) throws ServiceException;
}
