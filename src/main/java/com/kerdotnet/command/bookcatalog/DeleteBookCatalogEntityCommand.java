package com.kerdotnet.command.bookcatalog;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.IBookCatalogService;
import com.kerdotnet.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Delete of an existing BookCatalog Entity by Id
 * Yevhen Ivanov; 2018-04-25
 */
public class DeleteBookCatalogEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBookCatalogEntityCommand.class);
    private IBookCatalogService bookCatalogService;

    public DeleteBookCatalogEntityCommand() {
    }

    public DeleteBookCatalogEntityCommand(IBookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        boolean result;
        int bookCatalogId = 0;

        if (bookCatalogService == null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                bookCatalogService = serviceFactory.getBookCatalogService();
            } catch (ServiceException|DAOSystemException e) {
                LOGGER.debug("DeleteBookCatalogEntityCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }

        String bookCatalogIdParam = sessionRequestContent.getRequestParameter("bookcatalogid");
        if (bookCatalogIdParam != null)
            bookCatalogId = Integer.parseInt(bookCatalogIdParam);
        LOGGER.debug("Id of the book catalog is: " + bookCatalogId);

        page = ConfigurationManager.getProperty("path.page.main");
        try {
            result = bookCatalogService.deleteBookCatalogById(bookCatalogId);
            if (!result){
                throw new ServletException(MessageManager.getProperty("message.deleteerror"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
