package com.kerdotnet.command;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.BookCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.List;

/**
 * List of taken BookItems (all) for administrator
 * Yevhen Ivanov; 2018-04-30
 */
public class ListTakenBookItemsCommand implements IActionCommand {
    static final Logger LOGGER = LoggerFactory.getLogger(ListTakenBookItemsCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        //TODO: continue here
//        List<BookItem> bookItems;
//
//        String bookCatalogIdParam = sessionRequestContent.getRequestParameter("bookcatalogid");
//        int bookCatalogId = 0;
//        if (bookCatalogIdParam != null)
//            bookCatalogId = Integer.parseInt(bookCatalogIdParam);
//        else {
//            bookCatalogId = (int) sessionRequestContent.getSessionAttribute("bookcatalogid");
//        }
//
//        page = ConfigurationManager.getProperty("path.page.bookitems");
//
//        try {
//            bookItems = BookCatalogService.getBookItemsByBookCatalogIdOnShelves(bookCatalogId);
//            LOGGER.debug("Find book items: " + bookItems);
//            sessionRequestContent.setSessionAttribute("bookitemlist", bookItems, true);
//            sessionRequestContent.setSessionAttribute("bookcatalogid", bookCatalogId);
//            sessionRequestContent.setSessionAttribute("takenBooks", false);
//        } catch (ServiceException e) {
//            throw new ServletException(e);
//        }
//        return page;
    }
}
