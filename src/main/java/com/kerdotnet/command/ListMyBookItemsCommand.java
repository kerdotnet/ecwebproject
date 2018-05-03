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
 * List of taken by current user BookItems
 * Yevhen Ivanov; 2018-04-30
 */
public class ListMyBookItemsCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListMyBookItemsCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        List<BookItem> bookItems;

        page = ConfigurationManager.getProperty("path.page.bookitems");

        try {
            String login = (String) sessionRequestContent.getSessionAttribute("user");
            bookItems = BookItemService.getAllBookItemsTakenByConcreteUser(login);
            LOGGER.debug("Find book items: " + bookItems);
            sessionRequestContent.setSessionAttribute("bookitemlist", bookItems, true);
            sessionRequestContent.setRequestAttribute("mybooks", true);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
