package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.beans.Authority;
import com.kerdotnet.beans.User;
import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.dao.IAuthorityDAO;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.dao.transactionmanager.InTransaction;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.NotUniqueUserEmailException;
import com.kerdotnet.exceptions.NotUniqueUserLoginException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.kerdotnet.utility.PasswordValidator.checkPassword;

/**
 * Business Logic connected with authorization and registration
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginServiceImpl implements ILoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
    private IUserDAO userDAO;
    private IUserAuthorityDAO userAuthorityDAO;
    private IAuthorityDAO authorityDAO;

    public LoginServiceImpl(IUserDAO userDAO, IUserAuthorityDAO userAuthorityDAO, IAuthorityDAO authorityDAO) {
        this.userDAO = userDAO;
        this.userAuthorityDAO = userAuthorityDAO;
        this.authorityDAO = authorityDAO;
    }

    @Override
    public boolean checkLogin(String login, String passwrod) throws ServiceException {

        try {
            User user = userDAO.findUserByUserName(login);
            if ((user != null) &&
                    (checkPassword(passwrod, user.getPassword()))) {
                LOGGER.debug("Validation was done successfully");
                return true;
            }

            return false;
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in user authorization process. User was not retrieved successfully", e);
        }
    }

    @Override
    public boolean checkAdministratorRole(String login) throws ServiceException {

        try {
            boolean administratorRole = false;

            User user = userDAO.findUserByUserName(login);

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
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in the user roles validation", e);
        }
    }

    @Override
    @InTransaction
    public boolean addUser(User user) throws ServiceException, NotUniqueUserLoginException, NotUniqueUserEmailException {

        LOGGER.debug("User is " + user);
        try {
            boolean successFlag = userDAO.create(user);
            LOGGER.debug("Was user created: " + successFlag);
            if (successFlag) {
                UserAuthority userAuthority = new UserAuthority(user.getId(), 1);
                userAuthorityDAO.create(userAuthority);
            }
            return successFlag;
        } catch (NotUniqueUserEmailException | NotUniqueUserLoginException e) {
            throw e;
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in adding of a new user", e);
        }
    }

}
