package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.beans.User;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.transactionmanager.InTransaction;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


public class BookOperationServiceImpl implements IBookOperationService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookOperationServiceImpl.class);
    private IUserDAO userDAO;
    private IBookItemUserDAO bookItemUserDAO;

    public BookOperationServiceImpl(IUserDAO userDAO, IBookItemUserDAO bookItemUserDAO) {
        this.userDAO = userDAO;
        this.bookItemUserDAO = bookItemUserDAO;
    }

    @Override
    @InTransaction
    public boolean takeBookItemByIdByUser(String login, int bookItemId) throws ServiceException{
        try {
                boolean result = false;

                User user = userDAO.findUserByUserName(login);

                if (user != null){
                    BookItemUser bookItemUser = new BookItemUser(bookItemId,
                            user.getId(),
                            LocalDateTime.now(),
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
                BookItemUser bookItemUser = bookItemUserDAO.findActiveEntityByBookItemId(bookItemId);
                if (bookItemUser != null){
                    bookItemUser.setEnabled(false);
                    result = bookItemUserDAO.update(bookItemUser);
                }
                return result;
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the BookOperation service (returnBookItemById)", e);
        }
    }
}
