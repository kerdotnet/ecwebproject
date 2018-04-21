package com.kerdotnet.logic;

import com.kerdotnet.beans.Authority;
import com.kerdotnet.beans.User;
import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.dao.IAuthorityDAO;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.factory.AbstractDAOFactory;
import com.kerdotnet.dao.factory.DAOEnum;
import com.kerdotnet.dao.factory.IDAOFactory;
import com.kerdotnet.exceptions.DAOSystemException;
import org.apache.log4j.Logger;

import java.util.List;

import static com.kerdotnet.utility.PasswordValidator.checkPassword;
import static com.kerdotnet.utility.PasswordValidator.checkPasswordsOnEquality;
import static com.kerdotnet.utility.PasswordValidator.hashPassword;

/**
 * Business Logic connected with authorization and registration
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginLogic {
    static final Logger LOGGER = Logger.getLogger(LoginLogic.class);

    /**
     * Check if such credentials can be entered in the system
     * @param sessionRequestContent
     * @param login
     * @param passwrod
     * @return
     * @throws DAOSystemException
     */
    public static boolean checkLogin(SessionRequestContent sessionRequestContent, String login, String passwrod) throws DAOSystemException {
        User user = null;

        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = daoFactory.getUserDAO();
            user = userDAO.findUserByUserName(login);
            sessionRequestContent.setSessionAttribute("administrator",
                    checkAdministratorRole(user, daoFactory), true);
        } catch (DAOSystemException e) {
            LOGGER.error("Error in the login validation", e);
            throw e;
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
     * @param user
     * @param daoFactory
     * @return
     * @throws DAOSystemException
     */
    private static boolean checkAdministratorRole(User user, IDAOFactory daoFactory) throws DAOSystemException {
        IUserAuthorityDAO userAuthorityDAO = daoFactory.getUserAuthorityDAO();
        IAuthorityDAO authorityDAO = daoFactory.getAuthorityDAO();

        boolean administratorRole = false;

        List<UserAuthority> userAuthorities = userAuthorityDAO.findAllByUserId(user.getId());
        for (UserAuthority userAuthority:
             userAuthorities) {
            Authority authority = authorityDAO.findEntity(userAuthority.getAuthorityId());
            LOGGER.debug(authority.isUser());
            LOGGER.debug(authority.isAdministrator());
            if (authority.isAdministrator())
                    administratorRole = true;
        }

        return administratorRole;
    }

    /**
     * create a new user in database or return an error
     * @param sessionRequestContent
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
    public static boolean addUser(SessionRequestContent sessionRequestContent, String login, String email,
                                  String firstName, String lastName, String mobile,
                                  String password, String confirmPassword) throws DAOSystemException {
        boolean successFlag = false;
        if (!checkPasswordsOnEquality(password, confirmPassword))
                return false;
        User user = new User(login, hashPassword(password), email,
                firstName, lastName, mobile, true);

        try (IDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = daoFactory.getUserDAO();
            successFlag = userDAO.create(user);
            IUserAuthorityDAO userAuthorityDAO = daoFactory.getUserAuthorityDAO();
            UserAuthority userAuthority = new UserAuthority(user.getId(), 1);
            userAuthorityDAO.create(userAuthority);

            sessionRequestContent.setSessionAttribute("administrator",
                    checkAdministratorRole(user, daoFactory));
        } catch (DAOSystemException e) {
            LOGGER.error("Error in the create new user", e);
            throw e;
        }
        LOGGER.debug("Retrieved user: " + user + " for login String: " + login);
        return successFlag;
    }


}
