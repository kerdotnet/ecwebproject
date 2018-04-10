package com.kerdotnet.command;

import com.kerdotnet.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of Empty command in case of application logic Error
 * Yevhen Ivanov; 2018-04-09
 */

public class EmptyCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
