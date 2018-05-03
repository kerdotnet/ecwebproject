package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Edit of an existing BookCatalog Entity
 * Yevhen Ivanov; 2018-04-25
 */
public class EditBookCatalogEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditBookCatalogEntityCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        page = ConfigurationManager.getProperty("path.page.bookcatalogentity");

        sessionRequestContent.setRequestAttribute("editmode", true);

        return page;
    }
}
