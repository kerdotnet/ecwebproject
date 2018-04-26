package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;

import javax.servlet.ServletException;

/**
 * Implementation of Empty command in case of application service Error
 * Yevhen Ivanov; 2018-04-09
 */

public class EmptyCommand implements IActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
