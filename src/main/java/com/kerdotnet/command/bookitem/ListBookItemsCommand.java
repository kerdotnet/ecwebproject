package com.kerdotnet.command.bookitem;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.BookItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.List;

/**
 * List of BookItems by bookcatalogId
 * Yevhen Ivanov; 2018-04-23
 */
public class ListBookItemsCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListBookItemsCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        List<BookItem> bookItems;

        String bookCatalogIdParam = sessionRequestContent.getRequestParameter("bookcatalogid");
        int bookCatalogId = 0;
        if (bookCatalogIdParam != null)
            bookCatalogId = Integer.parseInt(bookCatalogIdParam);
        else {
            bookCatalogId = (int) sessionRequestContent.getSessionAttribute("bookcatalogid");
        }

        page = ConfigurationManager.getProperty("path.page.bookitems");

        try {
            bookItems = BookItemService.getBookItemsByBookCatalogIdOnShelves(bookCatalogId);
            LOGGER.debug("Find book items: " + bookItems);
            sessionRequestContent.setSessionAttribute("bookitemlist", bookItems, true);
            sessionRequestContent.setSessionAttribute("bookcatalogid", bookCatalogId);
            sessionRequestContent.setRequestAttribute("takenbooks", false);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
