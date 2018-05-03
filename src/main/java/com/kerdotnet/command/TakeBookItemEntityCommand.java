package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.BookOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Take the BookItem Entity by user
 * Yevhen Ivanov; 2018-04-30
 */
public class TakeBookItemEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(TakeBookItemEntityCommand.class);
    public static final String BOOKITEMID = "bookitemid";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page = ConfigurationManager.getProperty("path.page.main");

        int bookItemId = 0;
        String bookItemIdParam = sessionRequestContent.getRequestParameter(BOOKITEMID);

        if (bookItemIdParam != null)
            bookItemId = Integer.parseInt(bookItemIdParam);
        LOGGER.debug("Id of the book item is: " + bookItemId);

        String login = (String) sessionRequestContent.getSessionAttribute("user");

        try {
            if (!BookOperationService.takeBookItemByIdByUser(login, bookItemId)){
                throw new ServletException(MessageManager.getProperty("message.deleteerror"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        return page;
    }
}
