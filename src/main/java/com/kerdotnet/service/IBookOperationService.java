package com.kerdotnet.service;

import com.kerdotnet.exceptions.ServiceException;

/**
 * Logic related to interaction with user and book catalog
 * Yevhen Ivanov, 2018-05-09
 */
public interface IBookOperationService  extends IService{

    /**
     * process the moving BookItem from Library to current User (by login)
     * @param login
     * @param bookItemId
     * @return
     * @throws ServiceException
     */
    boolean takeBookItemByIdByUser(String login, int bookItemId) throws ServiceException;

    /**
     * return the BookItem from current User (by login) to the Library
     * @param bookItemId
     * @return
     * @throws ServiceException
     */
    boolean returnBookItemById(int bookItemId) throws ServiceException;

    boolean confirmTakeBookItemById(int bookItemId) throws ServiceException;

    boolean confirmReturnBookItemById(int bookItemId) throws ServiceException;
}
