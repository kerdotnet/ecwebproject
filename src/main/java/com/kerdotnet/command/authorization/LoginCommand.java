package com.kerdotnet.command.authorization;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.filter.ClientType;
import com.kerdotnet.service.ILoginService;
import com.kerdotnet.service.factory.ServiceFactory;
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
    private ILoginService loginService;

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    public LoginCommand() {
        ServiceFactory serviceFactory = null;
        try {
            serviceFactory = ServiceFactory.getInstance();
            loginService = serviceFactory.getLoginService();
        } catch (ServiceException e) {
            //TODO: throw Servlet exception and refactor CommandEnum that it can throw exception
            LOGGER.debug("Add user init error: " + e.getMessage());
        } catch (DAOSystemException e) {
            LOGGER.debug("Add user init error: " + e.getMessage());
        }
    }

    public LoginCommand(ILoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {

        String page;
        String login = sessionRequestContent.getRequestParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_NAME_PASSWORD);

        sessionRequestContent.setRequestAttribute("login", login);

        boolean loginResult;
        boolean isAdmin =false;

        try {
            loginResult = loginService.checkLogin(login, password);
            if (loginResult)
                isAdmin = loginService.checkAdministratorRole(login);
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
