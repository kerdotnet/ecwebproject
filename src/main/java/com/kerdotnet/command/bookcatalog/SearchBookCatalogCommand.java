package com.kerdotnet.command.bookcatalog;

import com.kerdotnet.entity.BookCatalog;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.IBookCatalogService;
import com.kerdotnet.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.List;

/**
 * Search request to BookCatalog
 * Yevhen Ivanov; 2018-05-01
 */
public class SearchBookCatalogCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchBookCatalogCommand.class);
    private static final String PARAM_SEARCH_REQUEST = "searchrequest";
    private static final int QUANTITY_PER_PAGE = 10;
    private static final String PARAM_NAME_CURRENT_PAGE = "currentpage";
    private static final String PARAM_NAME_SEARCH_REQUEST_ATRIBUTE = "searchrequestattribute";

    private IBookCatalogService bookCatalogService;

    public SearchBookCatalogCommand() {
    }

    public SearchBookCatalogCommand(IBookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        if (bookCatalogService == null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                bookCatalogService = serviceFactory.getBookCatalogService();
            } catch (ServiceException|DAOSystemException e) {
                LOGGER.debug("SearchBookCatalogCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }

        List<BookCatalog> bookCatalogs;

        page = ConfigurationManager.getProperty("path.page.bookcatalog");

        int currentPage = 1;
        String currentPageParam = sessionRequestContent.getRequestParameter(PARAM_NAME_CURRENT_PAGE);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_CURRENT_PAGE, currentPageParam);

        if (currentPageParam != null){
            currentPage = Integer.parseInt(currentPageParam);
        }

        String searchRequest = sessionRequestContent.getRequestParameter(PARAM_SEARCH_REQUEST);
        LOGGER.debug("parameter search request: " + searchRequest);

        if (searchRequest == null) {
            searchRequest = (String) sessionRequestContent.getSessionAttribute(PARAM_NAME_SEARCH_REQUEST_ATRIBUTE);
            LOGGER.debug("attribute search request: " + searchRequest);
        } else {
            sessionRequestContent.setSessionAttribute(PARAM_NAME_SEARCH_REQUEST_ATRIBUTE, searchRequest);
            LOGGER.debug("attribute was set: " + searchRequest);
        }

        try {
            int quantityOfBooks = bookCatalogService.getAllBookCatalogBySearchRequestFullTextQuantity(searchRequest);
            bookCatalogs = bookCatalogService.getAllBookCatalogBySearchRequestFullTextByPage(
                        searchRequest, (currentPage - 1) * QUANTITY_PER_PAGE, QUANTITY_PER_PAGE);

            int maxPages = (quantityOfBooks / QUANTITY_PER_PAGE) + 1;

            sessionRequestContent.setRequestAttribute("bookcataloglist", bookCatalogs);

            sessionRequestContent.setRequestAttribute("booksquantity", quantityOfBooks);
            sessionRequestContent.setRequestAttribute("currentpage", currentPage);
            sessionRequestContent.setRequestAttribute("maxpages", maxPages);
            sessionRequestContent.setRequestAttribute("command", "search");

            LOGGER.debug("quntity of books: " + quantityOfBooks);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
