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
 * List of taken BookItems (all) for administrator
 * Yevhen Ivanov; 2018-04-30
 */
public class ListTakenBookItemsCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListTakenBookItemsCommand.class);
    private IBookItemService bookItemService;

    private static final String REFRESH_COMMAND = "refreshcommand";

    public ListTakenBookItemsCommand() {
    }

    public ListTakenBookItemsCommand(IBookItemService bookItemService) {
        this.bookItemService = bookItemService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        List<BookItem> bookItems;
        if (bookItemService == null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                bookItemService = serviceFactory.getBookItemService();
            } catch (ServiceException|DAOSystemException e) {
                LOGGER.debug("ListTakenBookItemsCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }
        page = ConfigurationManager.getProperty("path.page.bookitems");

        try {
            bookItems = bookItemService.getAllBookItemsTakenByUsers();
            LOGGER.debug("Find book items: " + bookItems);
            sessionRequestContent.setSessionAttribute("bookitemlist", bookItems, true);
            sessionRequestContent.setRequestAttribute("takenbooks", true);
            sessionRequestContent.setSessionAttribute(REFRESH_COMMAND,
                    ConfigurationManager.getProperty("path.page.refreshtakenbookitem"));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
