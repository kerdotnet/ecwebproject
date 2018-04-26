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


/**
 * Implementation of Login command
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginCommand implements IActionCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {

        String page = null;
        String login = sessionRequestContent.getRequestParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_NAME_PASSWORD);

        sessionRequestContent.setRequestAttribute("login", login);

        boolean loginResult = false;
        boolean isAdmin = false;

        try {
            loginResult = LoginLogic.checkLogin(login, password);
            isAdmin = LoginLogic.checkAdministratorRole(login);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        if (loginResult) {
            sessionRequestContent.setSessionAttribute("user", login, true);

            if (isAdmin) {
                sessionRequestContent.setSessionAttribute("userType", ClientType.ADMINISTRATOR);
            } else
                sessionRequestContent.setSessionAttribute("userType", ClientType.USER);

            page = ConfigurationManager.getProperty("path.page.main");
            LOGGER.debug("Login accomplished successfully, return page: " +
                    page);
        } else {
            sessionRequestContent.setSessionAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
            LOGGER.debug("Error login, return page: " +
                    page);
        }
        return page;
    }
}
