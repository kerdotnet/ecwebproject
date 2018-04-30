package com.kerdotnet.service;

import com.kerdotnet.beans.Author;
import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.beans.BookCatalogAuthor;
import com.kerdotnet.beans.BookItem;
import com.kerdotnet.dao.IAuthorDAO;
import com.kerdotnet.dao.IBookCatalogAuthorDAO;
import com.kerdotnet.dao.IBookCatalogDAO;
import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.dao.connectionfactory.ConnectionFactory;
import com.kerdotnet.dao.connectionfactory.ConnectionFactoryFactory;
import com.kerdotnet.dao.daofactory.AbstractDAOFactory;
import com.kerdotnet.dao.daofactory.IDAOFactory;
import com.kerdotnet.dao.transaction.ITransactionManager;
import com.kerdotnet.dao.transaction.TransactionManagerImpl;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Logic related to BookCatalogs
 * Yevhen Ivanov, 2018-04-23
 */
public class BookCatalogService {
    static final Logger LOGGER = LoggerFactory.getLogger(BookCatalogService.class);

    public static BookCatalog getBookCatalogById(int id) throws ServiceException {
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {
                BookCatalog bookCatalogEntity;

                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
                bookCatalogEntity = bcDAO.findEntity(id);

                IBookCatalogAuthorDAO bcaDAO = daoFactory.getBookCatalogAuthorDAO();
                IAuthorDAO authorDAO = daoFactory.getAuthorDAO();

                List<BookCatalogAuthor> bookCatalogAuthor = bcaDAO.findAllByBookCatalogId(bookCatalogEntity.getId());
                List<Author> authors = getAuthorsByBookCatalogAuthors(authorDAO, bookCatalogAuthor);
                LOGGER.debug("Retrived authors: " + authors.toString());
                bookCatalogEntity.setAuthors(authors);

                LOGGER.debug("Retrieved BookCatalog Item: " + bookCatalogEntity);
                return bookCatalogEntity;
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }

    public static List<BookCatalog> getAllBookCatalog() throws ServiceException {
        List<BookCatalog> bookCatalogList;

        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            bookCatalogList = bcDAO.findAll();

            IBookCatalogAuthorDAO bookCatalogAuthorDAO = daoFactory.getBookCatalogAuthorDAO();
            IAuthorDAO authorDAO = daoFactory.getAuthorDAO();

            for (BookCatalog bookCatalog : bookCatalogList) {
                List<BookCatalogAuthor> bookCatalogAuthor =
                        bookCatalogAuthorDAO.findAllByBookCatalogId(bookCatalog.getId());
                LOGGER.debug("bookCatalogAuthor = " + bookCatalog.getId() +
                        ": "+ bookCatalogAuthor);
                List<Author> authors = getAuthorsByBookCatalogAuthors(authorDAO, bookCatalogAuthor);
                LOGGER.debug("getAllBookCatalog (book id - authors): " + bookCatalog.getId() +
                        ": "+ authors.toString());
                bookCatalog.setAuthors(authors);
            }
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
        LOGGER.debug("Retrieved BookCatalog Items: " + bookCatalogList);

        return bookCatalogList;
    }

    public static boolean deleteBookCatalogById(int bookCatalogId) throws ServiceException {
        //:TODO change cascading deleting to use flags
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {

                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookCatalogDAO bookCatalogDAO = daoFactory.getBookCatalogDAO();

                LOGGER.debug("book was deleted with Id: " + bookCatalogId);
                return bookCatalogDAO.delete(bookCatalogDAO.findEntity(bookCatalogId));
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }

     public static boolean saveBookCatalogEntity(BookCatalog bookCatalog) throws ServiceException  {
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {

                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookCatalogDAO bookCatalogDAO = daoFactory.getBookCatalogDAO();
                IBookCatalogAuthorDAO bookCatalogAuthorDAO = daoFactory.getBookCatalogAuthorDAO();

                boolean result;
                if (bookCatalogDAO.findEntity(bookCatalog.getId()) != null) {
                    result = bookCatalogDAO.update(bookCatalog);
                }
                else
                    result = bookCatalogDAO.create(bookCatalog);

                bookCatalogAuthorDAO.deleteByBookCatalogId(bookCatalog.getId());
                for (Author author : bookCatalog.getAuthors()) {
                    result = bookCatalogAuthorDAO.create(
                            new BookCatalogAuthor(bookCatalog.getId(),
                            author.getId()));
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

    public static List<Author> getAllAuthors() throws ServiceException {
        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IAuthorDAO authorDAO = daoFactory.getAuthorDAO();

            return authorDAO.findAll();
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

    public static Author findAuthorById(int id) throws ServiceException {
        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IAuthorDAO authorDAO = daoFactory.getAuthorDAO();

            return authorDAO.findEntity(id);
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

    private static LinkedList<Author> getAuthorsByBookCatalogAuthors(IAuthorDAO authorDAO, List<BookCatalogAuthor> bookCatalogAuthor) {
        return bookCatalogAuthor.stream()
                .map(item -> {
                    try {
                        return authorDAO.findEntity(item.getAuthorId());
                    } catch (DAOSystemException e) {
                        return null;
                    }
                })
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static List<BookItem> getBookItemsByBookCatalogId(int bookCatalogId) throws ServiceException{
        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();

            return bookItemDAO.findByBookCatalogId(bookCatalogId);
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

    public static boolean deleteBookItemById(int bookItemId) throws ServiceException {
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {

                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookItemDAO bookItemDAO = daoFactory.getBookItemDAO();

                LOGGER.debug("book was deleted with Id: " + bookItemId);
                return bookItemDAO.delete(bookItemDAO.findEntity(bookItemId));
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
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
}
