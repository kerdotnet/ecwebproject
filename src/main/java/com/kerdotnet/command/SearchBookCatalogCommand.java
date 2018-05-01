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
 * Search request to BookCatalog
 * Yevhen Ivanov; 2018-05-01
 */
public class SearchBookCatalogCommand implements IActionCommand {
    static final Logger LOGGER = LoggerFactory.getLogger(SearchBookCatalogCommand.class);
    private static final String PARAM_SEARCH_REQUEST = "searchrequest";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        List<BookCatalog> bookCatalogs;

        String searchRequest = sessionRequestContent.getRequestParameter(PARAM_SEARCH_REQUEST);

        page = ConfigurationManager.getProperty("path.page.bookcatalog");

        try {
            bookCatalogs = BookCatalogService.searchFullTextRequest(searchRequest);
            sessionRequestContent.setSessionAttribute("bookcataloglist", bookCatalogs, true);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
