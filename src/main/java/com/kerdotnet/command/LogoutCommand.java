package com.kerdotnet.command;

import com.kerdotnet.logic.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of Logout command
 * Yevhen Ivanov; 2018-04-09
 */

public class LogoutCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        if (LoginLogic.logout()){
            page = ConfigurationManager.getProperty("path.page.login");
        } else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
