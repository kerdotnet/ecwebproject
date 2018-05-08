package com.kerdotnet.command.authorization;

import com.kerdotnet.beans.User;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.NotUniqueUserEmailException;
import com.kerdotnet.exceptions.NotUniqueUserLoginException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.filter.ClientType;
import com.kerdotnet.service.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

import static com.kerdotnet.utility.PasswordValidator.hashPassword;
import static com.kerdotnet.utility.Validator.*;


/**
 * Implementation of Add user command
 * Yevhen Ivanov; 2018-04-14
 */

public class AddUserCommand implements IActionCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_FIRST_NAME = "firstName";
    private static final String PARAM_NAME_LAST_NAME = "lastName";
    private static final String PARAM_NAME_MOBILE = "mobile";
    private static final String PARAM_NAME_CONFIRMATION_PASSWORD = "passwordConfirmation";

    static final Logger LOGGER = LoggerFactory.getLogger(AddUserCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page = null;

        User user = getUserByParameters(sessionRequestContent);
        String password = sessionRequestContent.getRequestParameter(PARAM_NAME_CONFIRMATION_PASSWORD);
        String confirmPassword = sessionRequestContent.getRequestParameter(PARAM_NAME_CONFIRMATION_PASSWORD);

        saveRequestAttributes(sessionRequestContent, user);

        boolean loginResult = false;
        boolean isAdmin =false;
        boolean addUserResult = false;

        page = ConfigurationManager.getProperty("path.page.adduser");

        String validationMessage = getValidationResultString(
                user, password, confirmPassword);

        LOGGER.debug("Validation result: " + validationMessage);

        if (!"".equals(validationMessage)){
            LOGGER.debug("Validation error");
            sessionRequestContent.setRequestAttribute("errorAddUserMessage",
                    validationMessage);
            return page;
        }

        try {
            addUserResult = LoginLogic.addUser(user);
            loginResult = LoginLogic.checkLogin(user.getUsername(), user.getPassword());
            isAdmin = LoginLogic.checkAdministratorRole(user.getUsername());
        } catch (NotUniqueUserEmailException e){
            sessionRequestContent.setRequestAttribute("errorAddUserMessage",
                    MessageManager.getProperty("message.existemail"));
            return page;
        } catch (NotUniqueUserLoginException e){
            sessionRequestContent.setRequestAttribute("errorAddUserMessage",
                    MessageManager.getProperty("message.existlogin"));
            return page;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        if (addUserResult && loginResult) {
            sessionRequestContent.setSessionAttribute("user", user.getUsername(), true);

            if (isAdmin) {
                sessionRequestContent.setSessionAttribute("userType", ClientType.ADMINISTRATOR);
            } else
                sessionRequestContent.setSessionAttribute("userType", ClientType.USER);

            page = ConfigurationManager.getProperty("path.page.main");
            LOGGER.debug("Adding new user accomplished successfully, return page: " +
                    page);
        } else {
            sessionRequestContent.setRequestAttribute("errorAddUserMessage",
                    MessageManager.getProperty("message.addusererror"));
            page = ConfigurationManager.getProperty("path.page.adduser");
            LOGGER.debug("Error login, return page: " +
                    page);
        }
        return page;
    }

    private User getUserByParameters(SessionRequestContent sessionRequestContent) {

        String login = sessionRequestContent.getRequestParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_NAME_PASSWORD);
        String email = sessionRequestContent.getRequestParameter(PARAM_NAME_EMAIL);
        String firstName = sessionRequestContent.getRequestParameter(PARAM_NAME_FIRST_NAME);
        String lastName = sessionRequestContent.getRequestParameter(PARAM_NAME_LAST_NAME);
        String mobile = sessionRequestContent.getRequestParameter(PARAM_NAME_MOBILE);

        User user = new User(login, hashPassword(password), email,
                firstName, lastName, mobile, true);


        return user;
    }

    private void saveRequestAttributes(SessionRequestContent sessionRequestContent, User user){
        sessionRequestContent.setRequestAttribute(PARAM_NAME_LOGIN, user.getUsername());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_EMAIL, user.getEmail());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_FIRST_NAME, user.getFirstName());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_LAST_NAME, user.getLastName());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_MOBILE, user.getMobile());
    }

    private String getValidationResultString(User user, String password, String confirmPassword) {
        String validationMessage = "";

        if (!(validatePasswordsOnEquality(password, confirmPassword)
                &&validatePassword(password))){
            validationMessage = MessageManager.getProperty("message.validpassword");
        } else if (!validateLogin(user.getUsername())){
            validationMessage = MessageManager.getProperty("message.validlogin");
        } else if (!(validateName(user.getFirstName())
                &&validateName(user.getLastName()))){
            validationMessage = MessageManager.getProperty("message.validnames");
        } else if (!validateEmail(user.getEmail())){
            validationMessage = MessageManager.getProperty("message.validemail");
        } else if (!validateMobile(user.getMobile())){
            validationMessage = MessageManager.getProperty("message.validmobile");
        }
        return validationMessage;
    }
}
