package com.kerdotnet.command.bookcatalog;

import com.kerdotnet.entity.BookCatalog;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Adding of a new BookCatalog Entity
 * Yevhen Ivanov; 2018-04-25
 */
public class AddBookCatalogEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddBookCatalogEntityCommand.class);

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
