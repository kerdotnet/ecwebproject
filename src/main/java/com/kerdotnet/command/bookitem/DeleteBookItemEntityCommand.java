package com.kerdotnet.command.bookitem;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookItemService;
import com.kerdotnet.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Delete of an existing BookItem Entity by Id
 * Yevhen Ivanov; 2018-04-30
 */
public class DeleteBookItemEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBookItemEntityCommand.class);
    private IBookItemService bookItemService;
    private static final String REFRESH_COMMAND = "refreshcommand";

    public DeleteBookItemEntityCommand() {
    }

    public DeleteBookItemEntityCommand(IBookItemService bookItemService) {
        this.bookItemService = bookItemService;
    }


    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        int bookItemId = 0;

        if (bookItemService == null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                bookItemService = serviceFactory.getBookItemService();
            } catch (ServiceException|DAOSystemException e) {
                LOGGER.debug("DeleteBookItemEntityCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }

        String bookItemIdParam = sessionRequestContent.getRequestParameter("bookitemid");
        if (bookItemIdParam != null)
            bookItemId = Integer.parseInt(bookItemIdParam);
        LOGGER.debug("Id of the book item is: " + bookItemId);
        String page = (String) sessionRequestContent.getSessionAttribute(REFRESH_COMMAND);
        LOGGER.debug("Next page is " + page);

        try {
            if (!bookItemService.deleteBookItemById(bookItemId)){
                throw new ServletException(MessageManager.getProperty("message.deleteerror"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
