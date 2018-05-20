package com.kerdotnet.command.bookitem;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookOperationService;
import com.kerdotnet.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Confirm by administrator that the BookItem Entity was taken by user
 * Yevhen Ivanov; 2018-05-20
 */
public class ConfirmTakeBookItemEntityCommand implements IActionCommand {
    private IBookOperationService bookOperationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmTakeBookItemEntityCommand.class);
    private static final String BOOKITEMID = "bookitemid";
    private static final String REFRESH_COMMAND = "refreshcommand";

    public ConfirmTakeBookItemEntityCommand() {
        ServiceFactory serviceFactory = null;
        try {
            serviceFactory = ServiceFactory.getInstance();
            bookOperationService = serviceFactory.getBookOperationService();
        } catch (ServiceException e) {
            //TODO: throw Servlet exception and refactor CommandEnum that it can throw exception
            LOGGER.debug("return book item command error: " + e.getMessage());
        } catch (DAOSystemException e) {
            LOGGER.debug("return book item command error: " + e.getMessage());
        }
    }

    public ConfirmTakeBookItemEntityCommand(IBookOperationService bookOperationService) {
        this.bookOperationService = bookOperationService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        int bookItemId = 0;
        String bookItemIdParam = sessionRequestContent.getRequestParameter(BOOKITEMID);

        if (bookItemIdParam != null)
            bookItemId = Integer.parseInt(bookItemIdParam);
        LOGGER.debug("Id of the book item is: " + bookItemId);

        String login = (String) sessionRequestContent.getSessionAttribute("user");
        String page = (String) sessionRequestContent.getSessionAttribute(REFRESH_COMMAND);
        LOGGER.debug("Next page is " + page);

        try {
            if (!bookOperationService.confirmTakeBookItemById(bookItemId)){
                sessionRequestContent.setRequestAttribute("errorMessage",
                        MessageManager.getProperty("message.bookalreadytaken"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        return page;
    }
}
