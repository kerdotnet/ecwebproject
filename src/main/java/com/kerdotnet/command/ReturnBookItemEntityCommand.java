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
 * Return the BookItem Entity by administrator
 * Yevhen Ivanov; 2018-05-01
 */
public class ReturnBookItemEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnBookItemEntityCommand.class);
    private static final String BOOKITEMID = "bookitemid";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page = ConfigurationManager.getProperty("path.page.refreshtakenbookitem");

        int bookItemId = 0;
        String bookItemIdParam = sessionRequestContent.getRequestParameter(BOOKITEMID);

        if (bookItemIdParam != null)
            bookItemId = Integer.parseInt(bookItemIdParam);
        LOGGER.debug("Id of the book item is: " + bookItemId);

        try {
            if (!BookOperationService.returnBookItemById(bookItemId)){
                throw new ServletException(MessageManager.getProperty("message.businesslogicbookcatalog"));
            }
            sessionRequestContent.setRequestAttribute("takenbooks", true);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
