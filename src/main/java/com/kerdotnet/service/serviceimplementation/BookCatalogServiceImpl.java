package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.entity.*;
import com.kerdotnet.dao.*;
import com.kerdotnet.dao.transactionmanager.InTransaction;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BookCatalogServiceImpl implements IBookCatalogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCatalogServiceImpl.class);

    private IBookCatalogDAO bcDAO;
    private IBookCatalogAuthorDAO bookCatalogAuthorDAO;
    private IAuthorDAO authorDAO;
    private IBookItemDAO bookItemDAO;
    private IBookItemUserDAO bookItemUserDAO;

    public BookCatalogServiceImpl() {
    }

    public BookCatalogServiceImpl(IBookCatalogDAO bcDAO,
                                  IBookCatalogAuthorDAO bookCatalogAuthorDAO,
                                  IAuthorDAO authorDAO, IBookItemDAO bookItemDAO,
                                  IBookItemUserDAO bookItemUserDAO) {
        this.bcDAO = bcDAO;
        this.bookCatalogAuthorDAO = bookCatalogAuthorDAO;
        this.authorDAO = authorDAO;
        this.bookItemDAO = bookItemDAO;
        this.bookItemUserDAO = bookItemUserDAO;
    }

    @Override
    @InTransaction
    public BookCatalog getBookCatalogById(int id) throws ServiceException {
        try {
            BookCatalog bookCatalogEntity;

            bookCatalogEntity = bcDAO.findEntity(id);

            enrichOneBookCatalogWithAuthor(bookCatalogEntity);

            return bookCatalogEntity;
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }

    @Override
    public List<BookCatalog> getAllBookCatalogByPage(int page, int quantityAtPage) throws ServiceException {
        List<BookCatalog> bookCatalogList;

        try {
            bookCatalogList = bcDAO.findAllByPage(page, quantityAtPage);

            enrichBookCatalogListWithAuthors(bookCatalogList);
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }

        return bookCatalogList;
    }

    @Override
    public int getAllBookCatalogQuantity() throws ServiceException {
        int quantity;

        try {
            quantity = bcDAO.findQuantity();

        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }

        return quantity;
    }

    @Override
    public List<BookCatalog> getAllBookCatalogBySearchRequestFullTextByPage(
            String searchRequest, int page, int quantityAtPage) throws ServiceException {
        List<BookCatalog> bookCatalogList;

        try {
            bookCatalogList = bcDAO.findByKeywordsOrNameOrAuthorByPage(searchRequest, page, quantityAtPage);

            enrichBookCatalogListWithAuthors(bookCatalogList);
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }

        return bookCatalogList;
    }

    @Override
    public int getAllBookCatalogBySearchRequestFullTextQuantity(
            String searchRequest) throws ServiceException {
        int quantity;

        try {
            quantity = bcDAO.findQuantityByKeywordsOrNameOrAuthor(searchRequest);

        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }

        return quantity;
    }

    @Override
    public boolean deleteBookCatalogById(int bookCatalogId) throws ServiceException {
        try {
            boolean result;
            deleteBookCatalogAuthorsByBookCatalogId(bookCatalogId);

            deleteBookItemsByBookCatalogId(bookCatalogId);

            result = bcDAO.delete(bcDAO.findEntity(bookCatalogId));
            LOGGER.debug("book was deleted with Id: " + bookCatalogId);
            return result;
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }

    @Override
    public boolean saveBookCatalogEntity(BookCatalog bookCatalog) throws ServiceException {
        try {
            boolean result;
            if (bcDAO.findEntity(bookCatalog.getId()) != null) {
                result = bcDAO.update(bookCatalog);
            } else
                result = bcDAO.create(bookCatalog);

            bookCatalogAuthorDAO.deleteByBookCatalogId(bookCatalog.getId());
            for (Author author : bookCatalog.getAuthors()) {
                result = bookCatalogAuthorDAO.create(
                        new BookCatalogAuthor(bookCatalog.getId(),
                                author.getId()));
            }
            return result;
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        } catch (Exception e) {
            throw new ServiceException("Error in the BookCatalog service (getBookCatalogById)", e);
        }
    }

    @Override
    public List<Author> getAllAuthors() throws ServiceException {

        try {
            return authorDAO.findAll();
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }

    @Override
    public Author findAuthorById(int id) throws ServiceException {

        try {
            return authorDAO.findEntity(id);
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }

    private void deleteBookItemsByBookCatalogId(int bookCatalogId) throws DAOSystemException {
        List<BookItem> bookItemList = bookItemDAO.findByBookCatalogId(bookCatalogId);
        for (BookItem bookItem : bookItemList) {
            List<BookItemUser> bookItemUserList = bookItemUserDAO.findAllByBookItemId(bookItem.getId());
            for (BookItemUser bookItemUser : bookItemUserList)
                bookItemUserDAO.delete(bookItemUser);
            bookItemDAO.delete(bookItem);
        }
    }

    /**
     * delete all Authors for existing BookCatalog
     *
     * @param bookCatalogId
     * @throws DAOSystemException
     */
    private void deleteBookCatalogAuthorsByBookCatalogId(int bookCatalogId) throws DAOSystemException {
        List<BookCatalogAuthor> bookCatalogAuthorList = bookCatalogAuthorDAO.findAllByBookCatalogId(bookCatalogId);
        for (BookCatalogAuthor bookCatalogAuthor : bookCatalogAuthorList) {
            bookCatalogAuthorDAO.delete(bookCatalogAuthor);
        }
    }

    /**
     * return a list of Author by links BookCatalogAuthor
     *
     * @param bookCatalogAuthor
     * @return
     */
    private List<Author> getAuthorsByBookCatalogAuthors(List<BookCatalogAuthor> bookCatalogAuthor) {
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

    private void enrichOneBookCatalogWithAuthor(BookCatalog bookCatalogEntity) throws DAOSystemException {
        List<BookCatalogAuthor> bookCatalogAuthor = bookCatalogAuthorDAO.findAllByBookCatalogId(bookCatalogEntity.getId());
        List<Author> authors = getAuthorsByBookCatalogAuthors(bookCatalogAuthor);
        bookCatalogEntity.setAuthors(authors);
    }

    private void enrichBookCatalogListWithAuthors(List<BookCatalog> bookCatalogList) throws DAOSystemException {
        for (BookCatalog bookCatalog : bookCatalogList) {
            List<BookCatalogAuthor> bookCatalogAuthor =
                    bookCatalogAuthorDAO.findAllByBookCatalogId(bookCatalog.getId());
            List<Author> authors = getAuthorsByBookCatalogAuthors(bookCatalogAuthor);
            bookCatalog.setAuthors(authors);
        }
    }
}
