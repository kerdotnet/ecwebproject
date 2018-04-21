package com.kerdotnet.command;

import com.kerdotnet.logic.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

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
    public String execute(HttpServletRequest request) {

        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String firstName = request.getParameter(PARAM_NAME_FIRST_NAME);
        String lastName = request.getParameter(PARAM_NAME_LAST_NAME);
        String mobile = request.getParameter(PARAM_NAME_MOBILE);
        String confirmPassword = request.getParameter(PARAM_NAME_CONFIRMATION_PASSWORD);

        if (LoginLogic.addUser(login, email, firstName,
                lastName, mobile, password, confirmPassword)) {
            request.setAttribute("user", login);
            page = ConfigurationManager.getProperty("path.page.main");
            LOGGER.debug("Adding new user accomplished successfully, return page: " +
                    page);
        } else {
            request.setAttribute("errorAddUserMessage",
                    MessageManager.getProperty("message.addusererror"));
            page = ConfigurationManager.getProperty("path.page.adduser");
            LOGGER.debug("Error login, return page: " +
                    page);
        }
        return page;
    }
}
