package com.kerdotnet.command;

import com.kerdotnet.logic.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of Login command
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginCommand implements ActionCommand{

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login= request.getParameter(PARAM_NAME_LOGIN);
        String passwrod = request.getParameter(PARAM_NAME_PASSWORD);

        if (LoginLogic.checkLogin(login, passwrod)){
            request.setAttribute("user", login);
            page = ConfigurationManager.getProperty("path.page.main");
            LOGGER.info("Login accomplished successfully, return page: " +
                    page);
        } else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
            LOGGER.info("Error login, return page: " +
                    page);
        }
        return page;
    }
}
