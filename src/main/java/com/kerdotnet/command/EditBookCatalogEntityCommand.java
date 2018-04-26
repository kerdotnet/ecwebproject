package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Edit of an existing BookCatalog Entity
 * Yevhen Ivanov; 2018-04-25
 */
public class EditBookCatalogEntityCommand implements IActionCommand {
    static final Logger LOGGER = LoggerFactory.getLogger(EditBookCatalogEntityCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page = null;
        //TODO: EDIT THIS COMMAND
        /*BookCatalog bookCatalogEntity = null;
        int bookCatalogId = 0;

        String bookCatalogIdParam = sessionRequestContent.getRequestParameter("bookcatalogid");
        if (bookCatalogIdParam != null)
            bookCatalogId = Integer.parseInt(bookCatalogIdParam);
        LOGGER.debug("Id of the book catalog is: " + bookCatalogId);

        page = ConfigurationManager.getProperty("path.page.bookcatalogentity");
        try {
            bookCatalogEntity = BookCatalogService.getBookCatalogById(bookCatalogId);
            sessionRequestContent.setSessionAttribute("bookcatalogentity", bookCatalogEntity, true);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }*/
        return page;
    }
}
