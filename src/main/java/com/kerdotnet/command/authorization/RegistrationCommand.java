package com.kerdotnet.command.authorization;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;

import javax.servlet.ServletException;

/**
 * Implementation of Registration command
 * Yevhen Ivanov; 2018-04-14
 */

public class RegistrationCommand implements IActionCommand {
    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {

        String page = ConfigurationManager.getProperty("path.page.adduser");

        return page;
    }
}
