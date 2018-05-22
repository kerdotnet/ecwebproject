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
 * Add one author to BookCatalog
 * Yevhen Ivanov; 2018-04-25
 */
public class AddOneAuthorCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddOneAuthorCommand.class);
    private IBookCatalogService bookCatalogService;

    public AddOneAuthorCommand() {
    }

    public AddOneAuthorCommand(IBookCatalogService bookCatalogService) {
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
                LOGGER.debug("AddOneAuthorCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }

        page = ConfigurationManager.getProperty("path.page.bookcatalogauthors");

        String idAuthorString = sessionRequestContent.getRequestParameter("newauthor");
        int authorId = 0;
        LOGGER.debug("New author id: " + idAuthorString);
        if (idAuthorString != null)
            authorId = Integer.parseInt(idAuthorString);

        BookCatalog bookCatalog = (BookCatalog)
                sessionRequestContent.getSessionAttribute("bookcatalogentity");
        List<Author> authors;

        try {
            authors = bookCatalogService.getAllAuthors();
            sessionRequestContent.setRequestAttribute("authors", authors);
            LOGGER.debug("authors: " + authors);

            if (authorId != 0) {
                bookCatalog.addOneAuthor(bookCatalogService.findAuthorById(authorId));
            }

            sessionRequestContent.setRequestAttribute("bookcatalogentity", bookCatalog);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        return page;
    }
}
