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
 * List of BookCatalog - general data of books (by catalog) by authors
 * Yevhen Ivanov; 2018-04-23
 */
public class ListBookCatalogCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListBookCatalogCommand.class);
    private static final int QUANTITY_PER_PAGE = 10;
    private static final String PARAM_NAME_CURRENT_PAGE = "currentpage";

    private IBookCatalogService bookCatalogService;

    public ListBookCatalogCommand() {
        ServiceFactory serviceFactory = null;
        try {
            serviceFactory = ServiceFactory.getInstance();
            bookCatalogService = serviceFactory.getBookCatalogService();
        } catch (ServiceException e) {
            //TODO: throw Servlet exception and refactor CommandEnum that it can throw exception
            LOGGER.debug("Add user init error: " + e.getMessage());
        } catch (DAOSystemException e) {
            LOGGER.debug("Add user init error: " + e.getMessage());
        }
    }

    public ListBookCatalogCommand(IBookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        List<BookCatalog> bookCatalogs;

        page = ConfigurationManager.getProperty("path.page.bookcatalog");

        int currentPage = 1;
        String currentPageParam = sessionRequestContent.getRequestParameter(PARAM_NAME_CURRENT_PAGE);

        if (currentPageParam != null){
            currentPage = Integer.parseInt(currentPageParam);
        }

        try {

            int quantityOfBooks = bookCatalogService.getAllBookCatalogQuantity();
            bookCatalogs = bookCatalogService.getAllBookCatalogByPage(
                        (currentPage - 1) * QUANTITY_PER_PAGE, QUANTITY_PER_PAGE);

            int maxPages = (quantityOfBooks / QUANTITY_PER_PAGE) + 1;

            sessionRequestContent.setRequestAttribute("bookcataloglist", bookCatalogs);

            sessionRequestContent.setRequestAttribute("booksquantity", quantityOfBooks);
            sessionRequestContent.setRequestAttribute("currentpage", currentPage);
            sessionRequestContent.setRequestAttribute("maxpages", maxPages);
            sessionRequestContent.setRequestAttribute("command", "bookcatalog");

            LOGGER.debug("quntity of books: " + quantityOfBooks);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
