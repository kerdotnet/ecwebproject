package com.kerdotnet.command.bookcatalog;

import com.kerdotnet.entity.Author;
import com.kerdotnet.entity.BookCatalog;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.IBookCatalogService;
import com.kerdotnet.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.List;

/**
 * Edit of BookCatalog-Authors links Entity
 * Yevhen Ivanov; 2018-04-25
 */
public class EditBookCatalogAuthorsCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditBookCatalogAuthorsCommand.class);
    private IBookCatalogService bookCatalogService;

    private static final String PARAM_BOOK_CATALOG_NAME = "name";
    private static final String PARAM_BOOK_CATALOG_FULLNAME = "fullname";
    private static final String PARAM_BOOK_CATALOG_DESCRITPION = "description";
    private static final String PARAM_BOOK_CATALOG_KEYWORDS = "keywords";

    public EditBookCatalogAuthorsCommand() {
    }

    public EditBookCatalogAuthorsCommand(IBookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        if (bookCatalogService == null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                bookCatalogService = serviceFactory.getBookCatalogService();
            } catch (ServiceException|DAOSystemException e) {
                LOGGER.debug("EditBookCatalogAuthorsCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }

        page = ConfigurationManager.getProperty("path.page.bookcatalogauthors");

        BookCatalog bookCatalog = (BookCatalog) sessionRequestContent.getSessionAttribute("bookcatalogentity");

        if (bookCatalog == null)
            bookCatalog = new BookCatalog();

        bookCatalog.setName(sessionRequestContent.getRequestParameter(PARAM_BOOK_CATALOG_NAME));
        bookCatalog.setFullName(sessionRequestContent.getRequestParameter(PARAM_BOOK_CATALOG_FULLNAME));
        bookCatalog.setDescription(sessionRequestContent.getRequestParameter(PARAM_BOOK_CATALOG_DESCRITPION));
        bookCatalog.setKeywords(sessionRequestContent.getRequestParameter(PARAM_BOOK_CATALOG_KEYWORDS));

        LOGGER.debug("Book Catalog when saving the changes: " + bookCatalog);

        List<Author> authors;

        try {
            authors = bookCatalogService.getAllAuthors();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        sessionRequestContent.setSessionAttribute("bookcatalogentity", bookCatalog);
        sessionRequestContent.setRequestAttribute("authors", authors);

        return page;
    }
}
