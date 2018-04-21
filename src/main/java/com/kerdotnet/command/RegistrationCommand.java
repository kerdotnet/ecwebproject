package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;

/**
 * Implementation of Registration command
 * Yevhen Ivanov; 2018-04-14
 */

public class RegistrationCommand implements IActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page = null;

        page = ConfigurationManager.getProperty("path.page.adduser");

        return page;
    }
}
