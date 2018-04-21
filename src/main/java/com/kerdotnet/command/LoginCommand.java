package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.logic.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;


/**
 * Implementation of Login command
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginCommand implements IActionCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {

        String page = null;
        String login = sessionRequestContent.getRequestParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_NAME_PASSWORD);

        boolean loginResult = false;

        try {
            loginResult = LoginLogic.checkLogin(sessionRequestContent, login, password);
        } catch (DAOSystemException e) {
            sessionRequestContent.setSessionAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.businesslogic"));
            page = ConfigurationManager.getProperty("path.page.error");
            LOGGER.debug("Error in business logic, return page: " +
                    page);
        }

        if (loginResult) {
            sessionRequestContent.setSessionAttribute("user", login, true);
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
