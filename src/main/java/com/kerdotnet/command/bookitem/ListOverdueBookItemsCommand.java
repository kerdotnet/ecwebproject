package com.kerdotnet.command.bookitem;

import com.kerdotnet.entity.BookItem;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.IBookItemService;
import com.kerdotnet.service.factory.ServiceFactory;
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
    private IBookItemService bookItemService;

    private static final String REFRESH_COMMAND = "refreshcommand";

    public ListOverdueBookItemsCommand() {
        ServiceFactory serviceFactory = null;
        try {
            serviceFactory = ServiceFactory.getInstance();
            bookItemService = serviceFactory.getBookItemService();
        } catch (ServiceException e) {
            //TODO: throw Servlet exception and refactor CommandEnum that it can throw exception
            LOGGER.debug("Add user init error: " + e.getMessage());
        } catch (DAOSystemException e) {
            LOGGER.debug("Add user init error: " + e.getMessage());
        }
    }

    public ListOverdueBookItemsCommand(IBookItemService bookItemService) {
        this.bookItemService = bookItemService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        List<BookItem> bookItems;

        page = ConfigurationManager.getProperty("path.page.bookitems");

        try {
            bookItems = bookItemService.getOverdueBookItemsTakenByUsers();
            LOGGER.debug("Find book items: " + bookItems);
            sessionRequestContent.setSessionAttribute("bookitemlist", bookItems, true);
            sessionRequestContent.setRequestAttribute("takenbooks", true);
            sessionRequestContent.setRequestAttribute("overdue", true);
            sessionRequestContent.setSessionAttribute(REFRESH_COMMAND,
                    ConfigurationManager.getProperty("path.page.refreshoverduebookitems"));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
