package com.kerdotnet.service;

import com.kerdotnet.beans.Authority;
import com.kerdotnet.beans.User;
import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.dao.IAuthorityDAO;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.daofactory.AbstractDAOFactory;
import com.kerdotnet.dao.daofactory.IDAOFactory;
import com.kerdotnet.dao.transaction.ITransactionManager;
import com.kerdotnet.dao.transaction.TransactionManagerImpl;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.NotUniqueUserEmailException;
import com.kerdotnet.exceptions.NotUniqueUserLoginException;
import com.kerdotnet.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.kerdotnet.utility.PasswordValidator.checkPassword;

/**
 * Business Logic connected with authorization and registration
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginLogic {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginLogic.class);

    /**
     * Check if such credentials can be entered in the system
     * if yes return true
     *
     * @param login
     * @param passwrod
     * @return
     * @throws DAOSystemException
     */
    public static boolean checkLogin(String login, String passwrod) throws ServiceException {

        try {

            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
            IUserDAO userDAO = daoFactory.getUserDAO();
            User user = userDAO.findUserByUserName(login);
            if ((user != null) &&
                    (checkPassword(passwrod, user.getPassword()))) {
                LOGGER.debug("Validation was done successfully");
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new ServiceException("Error in user authorization process. User was not retrieved successfully", e);
        }
    }

    /**
     * return true if the user is Administrator
     * in other cases - false
     *
     * @param login
     * @return
     * @throws DAOSystemException
     */
    public static boolean checkAdministratorRole(String login) throws ServiceException {

        ITransactionManager txManager = new TransactionManagerImpl();

        try {
            boolean administratorRole = false;

            IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();

            IUserDAO userDAO = daoFactory.getUserDAO();
            User user = userDAO.findUserByUserName(login);

            IUserAuthorityDAO userAuthorityDAO = daoFactory.getUserAuthorityDAO();
            IAuthorityDAO authorityDAO = daoFactory.getAuthorityDAO();
            //TODO: rewrite with lambda
            List<UserAuthority> userAuthorities = userAuthorityDAO.findAllByUserId(user.getId());
            for (UserAuthority userAuthority :
                    userAuthorities) {
                Authority authority = authorityDAO.findEntity(userAuthority.getAuthorityId());
                if (authority.isAdministrator())
                    administratorRole = true;
            }
            LOGGER.debug("User is " + user);
            LOGGER.debug("administratorRole is " + administratorRole);
            return administratorRole;
        } catch (Exception e) {
            throw new ServiceException("Error in the user roles validation", e);
        }
    }

    /**
     * create a new user in database or return an error
     *
     * @param user
     * @return
     * @throws DAOSystemException
     */
    public static boolean addUser(User user) throws ServiceException, NotUniqueUserLoginException, NotUniqueUserEmailException {

        ITransactionManager txManager = new TransactionManagerImpl();
        LOGGER.debug("User is " + user);
        try {
            return txManager.doInTransaction(() -> {
                IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory();
                IUserDAO userDAO = daoFactory.getUserDAO();
                boolean successFlag = userDAO.create(user);
                LOGGER.debug("Was user created: " + successFlag);
                if (successFlag) {
                    IUserAuthorityDAO userAuthorityDAO = daoFactory.getUserAuthorityDAO();
                    UserAuthority userAuthority = new UserAuthority(user.getId(), 1);
                    userAuthorityDAO.create(userAuthority);
                }
                return successFlag;
            });
        } catch (NotUniqueUserEmailException|NotUniqueUserLoginException e){
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error in the adding of a new user in LoginLogic service", e);
        }
    }

}
