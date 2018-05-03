package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.BookCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Delete of an existing BookCatalog Entity by Id
 * Yevhen Ivanov; 2018-04-25
 */
public class DeleteBookCatalogEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBookCatalogEntityCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        boolean result;
        int bookCatalogId = 0;

        String bookCatalogIdParam = sessionRequestContent.getRequestParameter("bookcatalogid");
        if (bookCatalogIdParam != null)
            bookCatalogId = Integer.parseInt(bookCatalogIdParam);
        LOGGER.debug("Id of the book catalog is: " + bookCatalogId);

        page = ConfigurationManager.getProperty("path.page.main");
        try {
            result = BookCatalogService.deleteBookCatalogById(bookCatalogId);
            if (!result){
                throw new ServletException(MessageManager.getProperty("message.deleteerror"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
