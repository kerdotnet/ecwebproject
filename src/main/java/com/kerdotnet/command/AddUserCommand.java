package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.filter.ClientType;
import com.kerdotnet.service.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

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

        String login = sessionRequestContent.getRequestParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_NAME_PASSWORD);
        String email = sessionRequestContent.getRequestParameter(PARAM_NAME_EMAIL);
        String firstName = sessionRequestContent.getRequestParameter(PARAM_NAME_FIRST_NAME);
        String lastName = sessionRequestContent.getRequestParameter(PARAM_NAME_LAST_NAME);
        String mobile = sessionRequestContent.getRequestParameter(PARAM_NAME_MOBILE);
        String confirmPassword = sessionRequestContent.getRequestParameter(PARAM_NAME_CONFIRMATION_PASSWORD);

        sessionRequestContent.setRequestAttribute("login", login);
        sessionRequestContent.setRequestAttribute("email", email);
        sessionRequestContent.setRequestAttribute("firstName", firstName);
        sessionRequestContent.setRequestAttribute("lastName", lastName);
        sessionRequestContent.setRequestAttribute("mobile", mobile);


        boolean loginResult = false;
        boolean isAdmin = false;
        boolean addUserResult = false;

        String validationMessage = getValidationResultString(login, password, confirmPassword,
                email, firstName, lastName, mobile);

        if (!"".equals(validationMessage)){
            LOGGER.debug("Error in password format");
            page = ConfigurationManager.getProperty("path.page.adduser");
            sessionRequestContent.setRequestAttribute("errorAddUserMessage",
                    validationMessage);
            return page;
        }

        try {
            addUserResult = LoginLogic.addUser(login, email, firstName,
                    lastName, mobile, password, confirmPassword);
            loginResult = LoginLogic.checkLogin(login, password);
            isAdmin = LoginLogic.checkAdministratorRole(login);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        if (addUserResult && loginResult) {
            sessionRequestContent.setSessionAttribute("user", login, true);

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

    private String getValidationResultString(String login, String password, String confirmPassword, String email, String firstName,
                                             String lastName,String mobile) {
        String validationMessage = "";

        if (!(validatePasswordsOnEquality(password, confirmPassword)&&validatePassword(password))){
            validationMessage = MessageManager.getProperty("message.validpassword");
        } else if (!validateLogin(login)){
            validationMessage = MessageManager.getProperty("message.validlogin");
        } else if (!(validateName(firstName)&&validateName(lastName))){
            validationMessage = MessageManager.getProperty("message.validnames");
        } else if (!validateEmail(email)){
            validationMessage = MessageManager.getProperty("message.validemail");
        } else if (!validateMobile(mobile)){
            validationMessage = MessageManager.getProperty("message.validmobile");
        }
        return validationMessage;
    }
}
