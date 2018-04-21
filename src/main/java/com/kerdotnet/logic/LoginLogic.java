package com.kerdotnet.logic;

import com.kerdotnet.beans.User;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.factory.AbstractDAOFactory;
import com.kerdotnet.dao.factory.DAOEnum;
import com.kerdotnet.dao.factory.IDAOFactory;
import com.kerdotnet.exceptions.DAOSystemException;
import org.apache.log4j.Logger;

import static com.kerdotnet.utility.PasswordValidator.checkPassword;
import static com.kerdotnet.utility.PasswordValidator.checkPasswordsOnEquality;
import static com.kerdotnet.utility.PasswordValidator.hashPassword;

/**
 * Implementation of Login command
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

        try (IDAOFactory daoManager = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = (IUserDAO) daoManager.getDAO(DAOEnum.USER);
            user = userDAO.findUserByUserName(login);
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

        try (IDAOFactory daoManager = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = (IUserDAO) daoManager.getDAO(DAOEnum.USER);
            LOGGER.debug("dao user is obtained");
            successFlag = userDAO.create(user);
            LOGGER.debug("success flag is: " + successFlag);
        } catch (DAOSystemException e) {
            LOGGER.error("Error in the create new user", e);
            throw e;
        }
        LOGGER.debug("Retrieved user: " + user + " for login String: " + login);
        return successFlag;
    }


}
