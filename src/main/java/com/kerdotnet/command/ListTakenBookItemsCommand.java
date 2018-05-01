package com.kerdotnet.command;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.BookItemService;
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
        List<BookItem> bookItems;

        page = ConfigurationManager.getProperty("path.page.bookitems");

        try {
            bookItems = BookItemService.getAllBookItemsTakenByUsers();
            LOGGER.debug("Find book items: " + bookItems);
            sessionRequestContent.setSessionAttribute("bookitemlist", bookItems, true);
            sessionRequestContent.setRequestAttribute("takenbooks", true);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
