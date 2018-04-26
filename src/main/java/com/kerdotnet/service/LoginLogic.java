package com.kerdotnet.service;

import com.kerdotnet.beans.Authority;
import com.kerdotnet.beans.User;
import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.dao.IAuthorityDAO;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.factory.AbstractDAOFactory;
import com.kerdotnet.dao.factory.IDAOFactory;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.kerdotnet.utility.PasswordValidator.checkPassword;
import static com.kerdotnet.utility.PasswordValidator.hashPassword;

/**
 * Business Logic connected with authorization and registration
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginLogic {
    static final Logger LOGGER = LoggerFactory.getLogger(LoginLogic.class);

    /**
     * Check if such credentials can be entered in the system
     * if yes return true
     * @param login
     * @param passwrod
     * @return
     * @throws DAOSystemException
     */
    public static boolean checkLogin(String login, String passwrod) throws ServiceException {
        User user = null;

        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = daoFactory.getUserDAO();
            user = userDAO.findUserByUserName(login);
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in user authorization process. User was not retrieved successfully", e);
        }
        LOGGER.debug("Retrieved user: " + user + " for login String: " + login);

        if ((user != null) &&
                (checkPassword(passwrod, user.getPassword()))) {
            LOGGER.debug("Validation was done successfully");
            return true;
        }
        return false;
    }

    /**
     * return true if the user is Administrator
     * in other cases - false
     * @param login
     * @return
     * @throws DAOSystemException
     */
    public static boolean checkAdministratorRole(String login) throws ServiceException {
        User user = null;
        boolean administratorRole = false;

        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = daoFactory.getUserDAO();
            user = userDAO.findUserByUserName(login);
            IUserAuthorityDAO userAuthorityDAO = daoFactory.getUserAuthorityDAO();
            IAuthorityDAO authorityDAO = daoFactory.getAuthorityDAO();
            List<UserAuthority> userAuthorities = userAuthorityDAO.findAllByUserId(user.getId());
            for (UserAuthority userAuthority:
                    userAuthorities) {
                Authority authority = authorityDAO.findEntity(userAuthority.getAuthorityId());
                if (authority.isAdministrator())
                    administratorRole = true;
            }
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the user roles validation", e);
        }
        return administratorRole;
    }

    /**
     * create a new user in database or return an error
     * @param login
     * @param email
     * @param firstName
     * @param lastName
     * @param mobile
     * @param password
     * @param confirmPassword
     * @return
     * @throws DAOSystemException
     */
    public static boolean addUser(String login, String email,
                                  String firstName, String lastName, String mobile,
                                  String password, String confirmPassword) throws ServiceException{
        boolean successFlag = false;

        User user = new User(login, hashPassword(password), email,
                firstName, lastName, mobile, true);

        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = daoFactory.getUserDAO();
            successFlag = userDAO.create(user);
            if (successFlag) {
                IUserAuthorityDAO userAuthorityDAO = daoFactory.getUserAuthorityDAO();
                UserAuthority userAuthority = new UserAuthority(user.getId(), 1);
                userAuthorityDAO.create(userAuthority);
            }
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the adding of a new user in LoginLogic service", e);
        }
        LOGGER.debug("Retrieved user: " + user + " for login String: " + login);
        return successFlag;
    }

}
