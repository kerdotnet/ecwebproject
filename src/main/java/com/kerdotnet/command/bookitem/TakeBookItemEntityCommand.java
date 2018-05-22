package com.kerdotnet.command.bookitem;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookOperationService;
import com.kerdotnet.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Take the BookItem Entity by user
 * Yevhen Ivanov; 2018-04-30
 */
public class TakeBookItemEntityCommand implements IActionCommand {
    private IBookOperationService bookOperationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TakeBookItemEntityCommand.class);
    public static final String BOOKITEMID = "bookitemid";
    private static final String REFRESH_COMMAND = "refreshcommand";

    public TakeBookItemEntityCommand() {
    }

    public TakeBookItemEntityCommand(IBookOperationService bookOperationService) {
        this.bookOperationService = bookOperationService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        int bookItemId = 0;
        if (bookOperationService == null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                bookOperationService = serviceFactory.getBookOperationService();
            } catch (ServiceException|DAOSystemException e) {
                LOGGER.debug("TakeBookItemEntityCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }
        String bookItemIdParam = sessionRequestContent.getRequestParameter(BOOKITEMID);

        if (bookItemIdParam != null)
            bookItemId = Integer.parseInt(bookItemIdParam);
        LOGGER.debug("Id of the book item is: " + bookItemId);

        String login = (String) sessionRequestContent.getSessionAttribute("user");
        String page = (String) sessionRequestContent.getSessionAttribute(REFRESH_COMMAND);
        LOGGER.debug("Next page is " + page);

        try {
            if (!bookOperationService.takeBookItemByIdByUser(login, bookItemId)){
                sessionRequestContent.setRequestAttribute("errorMessage",
                        MessageManager.getProperty("message.bookalreadytaken"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        return page;
    }
}
