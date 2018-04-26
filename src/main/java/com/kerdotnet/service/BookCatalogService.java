package com.kerdotnet.service;

import com.kerdotnet.beans.Author;
import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.beans.BookCatalogAuthor;
import com.kerdotnet.dao.IAuthorDAO;
import com.kerdotnet.dao.IBookCatalogAuthorDAO;
import com.kerdotnet.dao.IBookCatalogDAO;
import com.kerdotnet.dao.factory.AbstractDAOFactory;
import com.kerdotnet.dao.factory.IDAOFactory;
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
        BookCatalog bookCatalogEntity = null;

        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            bookCatalogEntity = bcDAO.findEntity(id);

            IBookCatalogAuthorDAO bcaDAO = daoFactory.getBookCatalogAuthorDAO();
            IAuthorDAO authorDAO = daoFactory.getAuthorDAO();

            List<BookCatalogAuthor> bookCatalogAuthor = bcaDAO.findAllByBookCatalogId(bookCatalogEntity.getId());
            List<Author> authors = getAuthorsByBookCatalogAuthors(authorDAO, bookCatalogAuthor);
            LOGGER.debug("Retrived authors: " + authors.toString());
            bookCatalogEntity.setAuthors(authors);
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
        LOGGER.debug("Retrieved BookCatalog Item: " + bookCatalogEntity);

        return bookCatalogEntity;
    }

    /**
     * obtain all BookCatalog itens without any filters
     */
    public static List<BookCatalog> getAllBookCatalog() throws ServiceException {
        List<BookCatalog> bookCatalogList = new LinkedList<>();

        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IBookCatalogDAO bcDAO = daoFactory.getBookCatalogDAO();
            bookCatalogList = bcDAO.findAll();
            IBookCatalogAuthorDAO bcaDAO = daoFactory.getBookCatalogAuthorDAO();
            IAuthorDAO authorDAO = daoFactory.getAuthorDAO();
            for (BookCatalog bookCatalog : bookCatalogList) {
                List<BookCatalogAuthor> bookCatalogAuthor = bcaDAO.findAllByBookCatalogId(bookCatalog.getId());
                List<Author> authors = getAuthorsByBookCatalogAuthors(authorDAO, bookCatalogAuthor);
                LOGGER.debug("Retrived authors: " + authors.toString());
                bookCatalog.setAuthors(authors);
            }
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
        LOGGER.debug("Retrieved BookCatalog Items: " + bookCatalogList);

        return bookCatalogList;
    }

    /**
     * delete BookCatalog Entity by Id
     * @param bookCatalogId
     * @return
     * @throws ServiceException
     */
    public static boolean delteBookCatalogById(int bookCatalogId)  throws ServiceException {
        //:TODO add here a transaction
        boolean result = false;
        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IBookCatalogDAO bookCatalogDAO = daoFactory.getBookCatalogDAO();
            result = bookCatalogDAO.delete(bookCatalogDAO.findEntity(bookCatalogId));
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
        LOGGER.debug("book was deleted with Id: " + bookCatalogId);
        return result;
    }

    /**retrieve Authors
     * from List of bookCatalogAuthor
     * @param authorDAO
     * @param bookCatalogAuthor
     * @return
     */
    private static LinkedList<Author> getAuthorsByBookCatalogAuthors(IAuthorDAO authorDAO, List<BookCatalogAuthor> bookCatalogAuthor) {
        return bookCatalogAuthor.stream()
                .map(item->{
                    try{
                        return authorDAO.findEntity(item.getId());
                    }catch (DAOSystemException e){
                        return null;
                    }
                })
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
