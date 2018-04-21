package com.kerdotnet.command;

import com.kerdotnet.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of Logout command
 * Yevhen Ivanov; 2018-04-09
 */

public class LogoutCommand implements IActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        request.getSession().invalidate();
        page = ConfigurationManager.getProperty("path.page.login");

        return page;
    }
}
