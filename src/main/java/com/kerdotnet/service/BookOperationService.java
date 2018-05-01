package com.kerdotnet.service;

import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.beans.User;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.daofactory.AbstractDAOFactory;
import com.kerdotnet.dao.daofactory.IDAOFactory;
import com.kerdotnet.dao.transaction.ITransactionManager;
import com.kerdotnet.dao.transaction.TransactionManagerImpl;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;

import java.time.LocalDateTime;

/**
 * Logic related to interaction with user and book catalog
 * Yevhen Ivanov, 2018-04-30
 */
public class BookOperationService {

    public static boolean takeBookItemByIdByUser(String login, int bookItemId) throws ServiceException{
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {
                boolean result = false;
                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IUserDAO userDAO = daoFactory.getUserDAO();

                User user = userDAO.findUserByUserName(login);

                if (user != null){
                    IBookItemUserDAO bookItemUserDAO = daoFactory.getBookItemUserDAO();
                    BookItemUser bookItemUser = new BookItemUser(bookItemId,
                            user.getId(),
                            LocalDateTime.now(),
                            true);
                    result = bookItemUserDAO.create(bookItemUser);
                }
                return result;
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }

    public static boolean returnBookItemById(int bookItemId) throws ServiceException{
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {
                boolean result = false;
                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();

                IBookItemUserDAO bookItemUserDAO = daoFactory.getBookItemUserDAO();
                BookItemUser bookItemUser = bookItemUserDAO.findActiveEntityByBookItemId(bookItemId);
                if (bookItemUser != null){
                    bookItemUser.setEnabled(false);
                    result = bookItemUserDAO.update(bookItemUser);
                }
                return result;
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }
}
