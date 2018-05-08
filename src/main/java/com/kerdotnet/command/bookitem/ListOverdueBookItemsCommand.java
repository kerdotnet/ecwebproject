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
 * List of overdue BookItems witch were taken more than 1 month ago; for administrator
 * Yevhen Ivanov; 2018-05-01
 */
public class ListOverdueBookItemsCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListOverdueBookItemsCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        List<BookItem> bookItems;

        page = ConfigurationManager.getProperty("path.page.bookitems");

        try {
            bookItems = BookItemService.getOverdueBookItemsTakenByUsers();
            LOGGER.debug("Find book items: " + bookItems);
            sessionRequestContent.setSessionAttribute("bookitemlist", bookItems, true);
            sessionRequestContent.setRequestAttribute("takenbooks", true);
            sessionRequestContent.setRequestAttribute("overdue", true);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
