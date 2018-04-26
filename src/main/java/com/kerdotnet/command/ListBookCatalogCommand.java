package com.kerdotnet.command;

import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.BookCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.List;

/**
 * List of BookCatalog - general data of books (by catalog) by authors
 * Yevhen Ivanov; 2018-04-23
 */
public class ListBookCatalogCommand implements IActionCommand {
    static final Logger LOGGER = LoggerFactory.getLogger(ListBookCatalogCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page = null;

        List<BookCatalog> bookCatalogs = null;

        page = ConfigurationManager.getProperty("path.page.bookcatalog");

        try {
            bookCatalogs = BookCatalogService.getAllBookCatalog();
            sessionRequestContent.setSessionAttribute("bookcataloglist", bookCatalogs, true);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
