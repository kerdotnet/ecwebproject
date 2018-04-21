package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.logic.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

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

    static final Logger LOGGER = Logger.getLogger(AddUserCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page = null;

        String login = sessionRequestContent.getRequestParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_NAME_PASSWORD);
        String email = sessionRequestContent.getRequestParameter(PARAM_NAME_EMAIL);
        String firstName = sessionRequestContent.getRequestParameter(PARAM_NAME_FIRST_NAME);
        String lastName = sessionRequestContent.getRequestParameter(PARAM_NAME_LAST_NAME);
        String mobile = sessionRequestContent.getRequestParameter(PARAM_NAME_MOBILE);
        String confirmPassword = sessionRequestContent.getRequestParameter(PARAM_NAME_CONFIRMATION_PASSWORD);

        boolean addUserResult = false;

        try {
            addUserResult = LoginLogic.addUser(sessionRequestContent, login, email, firstName,
                    lastName, mobile, password, confirmPassword);
        } catch (DAOSystemException e) {
            sessionRequestContent.setSessionAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.businesslogic"));
            page = ConfigurationManager.getProperty("path.page.error");
            LOGGER.debug("Error in business logic, return page: " +
                    page);
        }

        if (addUserResult) {
            sessionRequestContent.setSessionAttribute("user", login, true);
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
}
