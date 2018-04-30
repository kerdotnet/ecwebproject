package com.kerdotnet.command;

import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.BookCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Adding of a new BookCatalog Entity
 * Yevhen Ivanov; 2018-04-25
 */
public class AddBookCatalogEntityCommand implements IActionCommand {
    static final Logger LOGGER = LoggerFactory.getLogger(AddBookCatalogEntityCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        BookCatalog bookCatalogEntity = new BookCatalog();

        page = ConfigurationManager.getProperty("path.page.bookcatalogentity");

        sessionRequestContent.setSessionAttribute("bookcatalogentity", bookCatalogEntity);
        sessionRequestContent.setRequestAttribute("editmode", true);

        return page;
    }
}
