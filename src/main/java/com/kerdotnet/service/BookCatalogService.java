package com.kerdotnet.service;

import com.kerdotnet.beans.*;
import com.kerdotnet.dao.*;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Services related to BookCatalogs and authors
 * Yevhen Ivanov, 2018-04-23
 */
public class BookCatalogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCatalogService.class);

    /**
     * get one BookCatalog Entity from DB by Id
     * @param id
     * @return
     * @throws ServiceException
     */
    public static BookCatalog getBookCatalogById(int id) throws ServiceException {
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {
                BookCatalog bookCatalogEntity;

                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
                bookCatalogEntity = bcDAO.findEntity(id);

                EnrichOneBookCatalogWithAuthor(bookCatalogEntity, daoFactory);

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

            EnrichBookCatalogListWithAuthors(bookCatalogList, daoFactory);
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

        return bookCatalogList;
    }

    /**
     * return all Book Catalog entities from DB by page
     * @param page
     * @param quantityAtPage
     * @return
     * @throws ServiceException
     */
    public static List<BookCatalog> getAllBookCatalogByPage(int page, int quantityAtPage) throws ServiceException {
        List<BookCatalog> bookCatalogList;

        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            bookCatalogList = bcDAO.findAllByPage(page, quantityAtPage);

            EnrichBookCatalogListWithAuthors(bookCatalogList, daoFactory);
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

        return bookCatalogList;
    }

    public static int getAllBookCatalogQuantity() throws ServiceException {
        int quantity;

        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            quantity = bcDAO.findQuantity();

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

        return quantity;
    }

    /**
     * return all Book Catalog entities from DB by text Query
     * @param searchRequest - the query to DB
     * @return
     * @throws ServiceException
     */
    public static List<BookCatalog> getAllBookCatalogBySearchRequestFullText(String searchRequest) throws ServiceException {
        List<BookCatalog> bookCatalogList;

        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            bookCatalogList = bcDAO.findByKeywordsOrNameOrAuthor(searchRequest);

            EnrichBookCatalogListWithAuthors(bookCatalogList, daoFactory);
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

        return bookCatalogList;
    }

    /**
     * Return a list of BookCatalog by searchRequest
     * by pages
     * @param searchRequest - specify the query to DB
     * @param page - the number of page
     * @param quantityAtPage - the quantity of elements at one page
     * @return
     * @throws ServiceException
     */
    public static List<BookCatalog> getAllBookCatalogBySearchRequestFullTextByPage(
            String searchRequest, int page, int quantityAtPage) throws ServiceException {
        List<BookCatalog> bookCatalogList;

        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            bookCatalogList = bcDAO.findByKeywordsOrNameOrAuthorByPage(searchRequest, page, quantityAtPage);

            EnrichBookCatalogListWithAuthors(bookCatalogList, daoFactory);
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

        return bookCatalogList;
    }

    /**
     * return a quantity of records in DB of BookCatalog
     * by the SearchRequest
     * @param searchRequest - specify the query to DB
     * @return
     * @throws ServiceException
     */
    public static int getAllBookCatalogBySearchRequestFullTextQuantity(
            String searchRequest) throws ServiceException {
        int quantity;

        ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

        try {
            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            quantity = bcDAO.findQuantityByKeywordsOrNameOrAuthor(searchRequest);

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

        return quantity;
    }

    public static boolean deleteBookCatalogById(int bookCatalogId) throws ServiceException {
        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            return txManager.doInTransaction(() -> {
                boolean result = false;
                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IBookCatalogDAO bookCatalogDAO = daoFactory.getBookCatalogDAO();

                deleteBookCatalogAuthorsByBookCatalogId(bookCatalogId, daoFactory);

                BookItemService.deleteBookItemsByBookCatalogId(bookCatalogId, daoFactory);

                result = bookCatalogDAO.delete(bookCatalogDAO.findEntity(bookCatalogId));
                LOGGER.debug("book was deleted with Id: " + bookCatalogId);
                return result;
            });
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }
    /**
     * Save a new (or existing) book catalog entity to DB
     * @param bookCatalog
     * @return
     * @throws ServiceException
     */
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

    /**
     * get a list of all Authors from DataBase
     * @return
     * @throws ServiceException
     */
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

    /**
     * find Author by Id
     * @param id
     * @return
     * @throws ServiceException
     */
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

    /**
     * delete all Authors for existing BookCatalog
     * @param bookCatalogId
     * @param daoFactory
     * @throws DAOSystemException
     */
    private static void deleteBookCatalogAuthorsByBookCatalogId(int bookCatalogId, IDAOFactory daoFactory) throws DAOSystemException {
        IBookCatalogAuthorDAO bookCatalogAuthorDAO = daoFactory.getBookCatalogAuthorDAO();
        List<BookCatalogAuthor> bookCatalogAuthorList = bookCatalogAuthorDAO.findAllByBookCatalogId(bookCatalogId);
        for (BookCatalogAuthor bookCatalogAuthor : bookCatalogAuthorList) {
            bookCatalogAuthorDAO.delete(bookCatalogAuthor);
        }
    }

    /**
     * return a list of Author by links BookCatalogAuthor
     * @param authorDAO
     * @param bookCatalogAuthor
     * @return
     */
    private static List<Author> getAuthorsByBookCatalogAuthors(IAuthorDAO authorDAO, List<BookCatalogAuthor> bookCatalogAuthor) {
        return bookCatalogAuthor.stream()
                .map(item -> {
                    try {
                        return authorDAO.findEntity(item.getAuthorId());
                    } catch (DAOSystemException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    private static void EnrichOneBookCatalogWithAuthor(BookCatalog bookCatalogEntity, IDAOFactory daoFactory) throws DAOSystemException {
        IBookCatalogAuthorDAO bcaDAO = daoFactory.getBookCatalogAuthorDAO();
        IAuthorDAO authorDAO = daoFactory.getAuthorDAO();

        List<BookCatalogAuthor> bookCatalogAuthor = bcaDAO.findAllByBookCatalogId(bookCatalogEntity.getId());
        List<Author> authors = getAuthorsByBookCatalogAuthors(authorDAO, bookCatalogAuthor);
        bookCatalogEntity.setAuthors(authors);
    }

    private static void EnrichBookCatalogListWithAuthors(List<BookCatalog> bookCatalogList, IDAOFactory daoFactory) throws DAOSystemException {
        IBookCatalogAuthorDAO bookCatalogAuthorDAO = daoFactory.getBookCatalogAuthorDAO();
        IAuthorDAO authorDAO = daoFactory.getAuthorDAO();

        for (BookCatalog bookCatalog : bookCatalogList) {
            List<BookCatalogAuthor> bookCatalogAuthor =
                    bookCatalogAuthorDAO.findAllByBookCatalogId(bookCatalog.getId());
            List<Author> authors = getAuthorsByBookCatalogAuthors(authorDAO, bookCatalogAuthor);
            bookCatalog.setAuthors(authors);
        }
    }
}
