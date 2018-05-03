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
 * List of BookCatalog - general data of books (by catalog) by authors
 * Yevhen Ivanov; 2018-04-23
 */
public class ViewBookCatalogEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewBookCatalogEntityCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        BookCatalog bookCatalogEntity;
        int bookCatalogId = 0;

        String bookCatalogIdParam = sessionRequestContent.getRequestParameter("bookcatalogid");
        if (bookCatalogIdParam != null)
            bookCatalogId = Integer.parseInt(bookCatalogIdParam);
        LOGGER.debug("Id of the book catalog is: " + bookCatalogId);

        page = ConfigurationManager.getProperty("path.page.bookcatalogentity");
        try {
            sessionRequestContent.setRequestAttribute("editmode", false);
            bookCatalogEntity = BookCatalogService.getBookCatalogById(bookCatalogId);
            sessionRequestContent.setSessionAttribute("bookcatalogentity", bookCatalogEntity);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
