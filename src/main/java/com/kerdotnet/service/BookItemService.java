package com.kerdotnet.service;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.beans.User;
import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.connectionfactory.ConnectionFactory;
import com.kerdotnet.dao.connectionfactory.ConnectionFactoryFactory;
import com.kerdotnet.dao.daofactory.AbstractDAOFactory;
import com.kerdotnet.dao.daofactory.IDAOFactory;
import com.kerdotnet.dao.transaction.ITransactionManager;
import com.kerdotnet.dao.transaction.TransactionManagerImpl;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Services related to BookItems
 * Yevhen Ivanov, 2018-04-23
 */
public class BookItemService {
    static void deleteBookItemsByBookCatalogId(int bookCatalogId, IDAOFactory daoFactory) throws DAOSystemException {
        IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();
        IBookItemUserDAO bookItemUserDAO = daoFactory.getBookItemUserDAO();

        List<BookItem> bookItemList = bookItemDAO.findByBookCatalogId(bookCatalogId);
        for (BookItem bookItem : bookItemList) {
            List<BookItemUser> bookItemUserList = bookItemUserDAO.findAllByBookItemId(bookItem.getId());
            for (BookItemUser bookItemUser : bookItemUserList)
                bookItemUserDAO.delete(bookItemUser);
            bookItemDAO.delete(bookItem);
        }
    }

    public static List<BookItem> getBookItemsByBookCatalogIdOnShelves(int bookCatalogId) throws ServiceException {
        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();

            return bookItemDAO.findByBookCatalogIdOnShelves(bookCatalogId);
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } finally {
            try {
                connectionFactory.closeConnection();
            } catch (DAOSystemException e) {
                throw new ServiceException(
                        MessageManager.getProperty("message.businesslogicbookcatalog"), e);
            }
        }
    }

    public static List<BookItem> getOverdueBookItemsTakenByUsers() throws ServiceException {
        LocalDateTime compareInterval = LocalDateTime.now().minusMonths(1);
        return getAllBookItemsTakenByUsers().stream()
                .filter(entity->entity.getBookItemUser().getDate().isBefore(compareInterval))
                .collect(Collectors.toList());
    }

    public static List<BookItem> getAllBookItemsTakenByConcreteUser(String login) throws ServiceException{
        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            List<BookItem> bookItemList = new LinkedList<>();
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IUserDAO userDAO = daoFactory.getUserDAO();
            User user = userDAO.findUserByUserName(login);

            if (user != null) {
                IBookItemUserDAO bookItemUserDAO = daoFactory.getBookItemUserDAO();
                List<BookItemUser> bookItemUserList = bookItemUserDAO.findAllByUserId(user.getId());

                IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();

                bookItemList = bookItemUserList.stream().map(item->{
                    try {
                        return bookItemDAO.findEntity(item.getBookItemId());
                    } catch (DAOSystemException e) {
                        return null;
                    }
                }).collect(Collectors.toList());
            }

            return bookItemList;
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } finally {
            try {
                connectionFactory.closeConnection();
            } catch (DAOSystemException e) {
                throw new ServiceException(
                        MessageManager.getProperty("message.businesslogicbookcatalog"), e);
            }
        }
    }

    public static List<BookItem> getAllBookItemsTakenByUsers() throws ServiceException{
        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();

            return bookItemDAO.findAllBookItemsTakenByUsers();
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } finally {
            try {
                connectionFactory.closeConnection();
            } catch (DAOSystemException e) {
                throw new ServiceException(
                        MessageManager.getProperty("message.businesslogicbookcatalog"), e);
            }
        }
    }

    public static boolean saveBookItemEntity(BookItem bookItem) throws ServiceException {
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {

                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();

                boolean result;

                if (bookItemDAO.findEntity(bookItem.getId()) != null) {
                    result = bookItemDAO.update(bookItem);
                }
                else
                    result = bookItemDAO.create(bookItem);

                return result;
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }

    public static boolean deleteBookItemById(int bookItemId) throws ServiceException {
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {

                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();

                BookCatalogService.LOGGER.debug("book was deleted with Id: " + bookItemId);
                return bookItemDAO.delete(bookItemDAO.findEntity(bookItemId));
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }
}
