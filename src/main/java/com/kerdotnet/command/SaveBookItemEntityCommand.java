package com.kerdotnet.command;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import com.kerdotnet.service.BookItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Save changes in BookItem Entity
 * Yevhen Ivanov; 2018-04-30
 */
public class SaveBookItemEntityCommand implements IActionCommand {
    static final Logger LOGGER = LoggerFactory.getLogger(SaveBookItemEntityCommand.class);

    private static final String PARAM_BOOK_CATALOG_DESCRIPTION = "description";
    private static final String PARAM_BOOK_CATALOG_BOOKSHELF = "bookshelf";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        boolean result;

        int bookCatalogId = 0;

        if (sessionRequestContent.getSessionAttribute("bookcatalogid") != null)
            bookCatalogId = (int) sessionRequestContent.getSessionAttribute("bookcatalogid");;

        BookItem bookItem = new BookItem();

        bookItem.setBookCatalogId(bookCatalogId);
        bookItem.setDescription(sessionRequestContent.getRequestParameter(PARAM_BOOK_CATALOG_DESCRIPTION));
        bookItem.setBookShelfAddress(sessionRequestContent.getRequestParameter(PARAM_BOOK_CATALOG_BOOKSHELF));
        bookItem.setEnabled(true);

        LOGGER.debug("Book Item when saving the changes: " + bookItem);

        page = ConfigurationManager.getProperty("path.page.refreshbookitem");
        try {
            result = BookItemService.saveBookItemEntity(bookItem);
            if (!result){
                throw new ServletException(MessageManager.getProperty("message.editerror"));
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
