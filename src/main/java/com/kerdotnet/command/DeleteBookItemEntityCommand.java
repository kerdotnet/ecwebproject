package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.BookItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Delete of an existing BookItem Entity by Id
 * Yevhen Ivanov; 2018-04-30
 */
public class DeleteBookItemEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBookItemEntityCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page = ConfigurationManager.getProperty("path.page.refreshbookitem");
        int bookItemId = 0;

        String bookItemIdParam = sessionRequestContent.getRequestParameter("bookitemid");
        if (bookItemIdParam != null)
            bookItemId = Integer.parseInt(bookItemIdParam);
        LOGGER.debug("Id of the book item is: " + bookItemId);

        try {
            if (!BookItemService.deleteBookItemById(bookItemId)){
                throw new ServletException(MessageManager.getProperty("message.deleteerror"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
