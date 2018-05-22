package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.entity.BookItem;
import com.kerdotnet.entity.BookItemStatus;
import com.kerdotnet.entity.BookItemUser;
import com.kerdotnet.entity.User;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.transactionmanager.InTransaction;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookOperationService;
import com.kerdotnet.utility.executors.EmailSendExecutorFactory;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;

import static com.kerdotnet.utility.EmailNotification.sendEmailToUser;


public class BookOperationServiceImpl implements IBookOperationService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookOperationServiceImpl.class);
    private IUserDAO userDAO;
    private IBookItemDAO bookItemDAO;
    private IBookItemUserDAO bookItemUserDAO;

    public BookOperationServiceImpl() {
    }

    public BookOperationServiceImpl(IUserDAO userDAO, IBookItemDAO bookItemDAO, IBookItemUserDAO bookItemUserDAO) {
        this.userDAO = userDAO;
        this.bookItemDAO = bookItemDAO;
        this.bookItemUserDAO = bookItemUserDAO;
    }

    @Override
    @InTransaction
    public boolean takeBookItemByIdByUser(String login, int bookItemId) throws ServiceException{
        try {
                boolean result = false;

                User user = userDAO.findUserByUserName(login);

                if (user != null){
                    BookItemUser bookItemUser =
                            bookItemUserDAO.findActiveEntityByBookItemId(bookItemId);
                    if (bookItemUser != null) {
                        return false;
                    }
                    bookItemUser = new BookItemUser(bookItemId,
                            user.getId(),
                            LocalDateTime.now(),
                            BookItemStatus.REQUESTED,
                            true);
                    result = bookItemUserDAO.create(bookItemUser);
                }
                return result;
        } catch (DAOSystemException e) {
            throw new ServiceException(
                    MessageManager.getProperty("message.businesslogicbookcatalog"), e);
        }
    }


    @Override
    @InTransaction
    public boolean returnBookItemById(int bookItemId) throws ServiceException{
        try {
                boolean result = false;
                BookItemUser bookItemUser = bookItemUserDAO.findActiveEntityByBookItemIdByStatus(
                        bookItemId, BookItemStatus.TAKEN.name());
                if (bookItemUser != null){
                    bookItemUser.setStatusByEnum(BookItemStatus.TORETURN);
                    result = bookItemUserDAO.update(bookItemUser);
                }
                return result;
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the BookOperation service (returnBookItemById)", e);
        }
    }

    @Override
    @InTransaction
    public boolean confirmTakeBookItemById(int bookItemId) throws ServiceException {
        try {
            boolean result = false;
            BookItemUser bookItemUser = bookItemUserDAO.findActiveEntityByBookItemIdByStatus(
                    bookItemId, BookItemStatus.REQUESTED.name());
            if (bookItemUser != null){
                bookItemUser.setStatusByEnum(BookItemStatus.TAKEN);
                result = bookItemUserDAO.update(bookItemUser);
            }
            return result;
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the BookOperation service (returnBookItemById)", e);
        }
    }

    @Override
    @InTransaction
    public boolean confirmReturnBookItemById(int bookItemId) throws ServiceException {
        try {
            boolean result = false;
            BookItemUser bookItemUser = bookItemUserDAO.findActiveEntityByBookItemIdByStatus(
                    bookItemId, BookItemStatus.TORETURN.name());
            if (bookItemUser != null){
                bookItemUser.setEnabled(false);
                bookItemUser.setStatusByEnum(BookItemStatus.RETURNED);
                result = bookItemUserDAO.update(bookItemUser);
            }
            return result;
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the BookOperation service (returnBookItemById)", e);
        }
    }

    @Override
    public boolean notifyUserByEmail(int bookItemId) throws ServiceException {
        try {
            BookItemUser bookItemUser = bookItemUserDAO.findActiveEntityByBookItemId(
                    bookItemId);
            if (bookItemUser != null){
                BookItem bookItem = bookItemDAO.findEntity(bookItemUser.getBookItemId());
                User user = userDAO.findEntity(bookItemUser.getUserId());
                LOGGER.debug("book item is: " + bookItem.getDescription());
                LOGGER.debug("user email is: " + user.getEmail());
                //TODO: start new thread
                ThreadPoolExecutor executor = EmailSendExecutorFactory.getExecutor();
                executor.execute(() -> {
                    try {
                        sendEmailToUser(user, bookItem);
                    } catch (EmailException e) {
                        LOGGER.debug("Error in sending email in the BookOperation service (notifyUserByEmail)", e);
                    }
                });
            }
            return true;
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the BookOperation service (notifyUserByEmail)", e);
        }
    }
}
