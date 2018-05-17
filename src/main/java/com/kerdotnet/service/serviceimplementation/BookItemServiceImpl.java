package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.entity.BookItem;
import com.kerdotnet.entity.BookItemUser;
import com.kerdotnet.entity.User;
import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.transactionmanager.InTransaction;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BookItemServiceImpl implements IBookItemService{
    static final Logger LOGGER = LoggerFactory.getLogger(BookItemServiceImpl.class);
    public static final int OVERDUE_DURATION_IN_MONTHS = 1;

    private IBookItemUserDAO bookItemUserDAO;
    private IBookItemDAO bookItemDAO;
    private IUserDAO userDAO;

    public BookItemServiceImpl() {
    }

    public BookItemServiceImpl(IUserDAO userDAO, IBookItemDAO bookItemDAO, IBookItemUserDAO bookItemUserDAO) {
        this.bookItemUserDAO = bookItemUserDAO;
        this.bookItemDAO = bookItemDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<BookItem> getBookItemsByBookCatalogIdOnShelves(int bookCatalogId) throws ServiceException {
        try {
            return bookItemDAO.findByBookCatalogIdOnShelves(bookCatalogId);
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }

    @Override
    public List<BookItem> getOverdueBookItemsTakenByUsers() throws ServiceException {
        LocalDateTime compareInterval = LocalDateTime.now().minusMonths(OVERDUE_DURATION_IN_MONTHS);
        return getAllBookItemsTakenByUsers().stream()
                .filter(entity->entity.getBookItemUser().getDate().isBefore(compareInterval))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookItem> getAllBookItemsTakenByConcreteUser(String login) throws ServiceException{
        try {
            List<BookItem> bookItemList = new LinkedList<>();
            User user = userDAO.findUserByUserName(login);

            if (user != null) {
                List<BookItemUser> bookItemUserList = bookItemUserDAO.findAllByUserId(user.getId());
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
        }
    }

    @Override
    public List<BookItem> getAllBookItemsTakenByUsers() throws ServiceException{
        try {
            return bookItemDAO.findAllBookItemsTakenByUsers();
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }

    @Override
    @InTransaction
    public boolean saveBookItemEntity(BookItem bookItem) throws ServiceException {

        try {
            boolean result;
            if (bookItemDAO.findEntity(bookItem.getId()) != null) {
                result = bookItemDAO.update(bookItem);
            } else
                result = bookItemDAO.create(bookItem);

            return result;
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }

    @Override
    @InTransaction
    public boolean deleteBookItemById(int bookItemId) throws ServiceException {
        try {
                LOGGER.debug("book was deleted with Id: " + bookItemId);
                return bookItemDAO.delete(bookItemDAO.findEntity(bookItemId));
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }
}
