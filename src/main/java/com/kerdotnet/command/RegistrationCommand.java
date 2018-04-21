package com.kerdotnet.command;

import com.kerdotnet.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of Registration command
 * Yevhen Ivanov; 2018-04-14
 */

public class RegistrationCommand implements IActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        page = ConfigurationManager.getProperty("path.page.adduser");

        return page;
    }
}
