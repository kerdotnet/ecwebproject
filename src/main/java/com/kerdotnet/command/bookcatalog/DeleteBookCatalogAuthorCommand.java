package com.kerdotnet.command.bookcatalog;

import com.kerdotnet.beans.Author;
import com.kerdotnet.beans.BookCatalog;
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
 * Delete one author from BookCatalog
 * Yevhen Ivanov; 2018-04-30
 */
public class DeleteBookCatalogAuthorCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBookCatalogAuthorCommand.class);
    private IBookCatalogService bookCatalogService;

    public DeleteBookCatalogAuthorCommand() {
        ServiceFactory serviceFactory = null;
        try {
            serviceFactory = ServiceFactory.getInstance();
            bookCatalogService = serviceFactory.getBookCatalogService();
        } catch (ServiceException e) {
            //TODO: throw Servlet exception and refactor CommandEnum that it can throw exception
            LOGGER.debug("Add user init error: " + e.getMessage());
        } catch (DAOSystemException e) {
            LOGGER.debug("Add user init error: " + e.getMessage());
        }
    }

    public DeleteBookCatalogAuthorCommand(IBookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        page = ConfigurationManager.getProperty("path.page.bookcatalogauthors");

        String idAuthorString = sessionRequestContent.getRequestParameter("deleteauthorid");
        int authorId = 0;
        LOGGER.debug("Author id to be deleted: " + idAuthorString);
        if (idAuthorString != null)
            authorId = Integer.parseInt(idAuthorString);

        BookCatalog bookCatalog = (BookCatalog)
                sessionRequestContent.getSessionAttribute("bookcatalogentity");
        List<Author> authors;
        LOGGER.debug("before deleteing: " + bookCatalog.getAuthors());
        try {
            bookCatalog.deleteOneAuthor(bookCatalogService.findAuthorById(authorId));
            sessionRequestContent.setRequestAttribute("bookcatalogentity", bookCatalog);
            LOGGER.debug("after deleteing: " + bookCatalog.getAuthors());

            authors = bookCatalogService.getAllAuthors();
            sessionRequestContent.setRequestAttribute("authors", authors);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
