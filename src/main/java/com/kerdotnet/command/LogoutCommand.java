package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;

import javax.servlet.ServletException;

/**
 * Implementation of Logout command
 * Yevhen Ivanov; 2018-04-09
 */

public class LogoutCommand implements IActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {

        sessionRequestContent.invalidateSession();
        String page = ConfigurationManager.getProperty("path.page.login");

        return page;
    }
}
