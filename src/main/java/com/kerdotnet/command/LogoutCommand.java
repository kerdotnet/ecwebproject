package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;

/**
 * Implementation of Logout command
 * Yevhen Ivanov; 2018-04-09
 */

public class LogoutCommand implements IActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page = null;

        sessionRequestContent.invalidateSession();
        page = ConfigurationManager.getProperty("path.page.login");

        return page;
    }
}
