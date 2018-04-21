package com.kerdotnet.logic;

import com.kerdotnet.beans.User;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.factory.AbstractDAOFactory;
import com.kerdotnet.dao.factory.DAOEnum;
import com.kerdotnet.dao.factory.IDAOFactory;
import com.kerdotnet.dao.factory.MySQLDAOFactory;
import com.kerdotnet.exceptions.DAOConfigurationException;
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

    public static boolean checkLogin(String login, String passwrod) {
        User user = null;

        try (IDAOFactory daoManager = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = (IUserDAO) daoManager.getDAO(DAOEnum.USER);
            user = userDAO.findUserByUserName(login);
        } catch (DAOSystemException e) {
            LOGGER.error("Error in the login validation", e);
        }
        LOGGER.debug("Retrieved user: " + user + " for login String: " + login);

        if ((user != null) &&
                (checkPassword(passwrod, user.getPassword()))) {
            LOGGER.debug("Validation was done successfully");
            return true;
        }
        return false;
    }

    public static boolean addUser(String login, String email,
                                  String firstName, String lastName, String mobile,
                                  String password, String confirmPassword) {
        boolean successFlag = false;
        if (!checkPasswordsOnEquality(password, confirmPassword))
                return false;
        User user = new User(login, hashPassword(password), email,
                firstName, lastName, mobile, true);
        LOGGER.debug("user entityt created: " + user);

        try (IDAOFactory daoManager = AbstractDAOFactory.getDAOFactory(1)) {
            IUserDAO userDAO = (IUserDAO) daoManager.getDAO(DAOEnum.USER);
            LOGGER.debug("dao user is obtained");
            successFlag = userDAO.create(user);
            LOGGER.debug("success flag is: " + successFlag);
        } catch (DAOSystemException e) {
            LOGGER.error("Error in the create new user", e);
        }
        LOGGER.debug("Retrieved user: " + user + " for login String: " + login);
        return successFlag;
    }


}
