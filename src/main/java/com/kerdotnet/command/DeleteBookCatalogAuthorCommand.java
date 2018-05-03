package com.kerdotnet.command;

import com.kerdotnet.beans.Author;
import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.BookCatalogService;
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
            bookCatalog.deleteOneAuthor(BookCatalogService.findAuthorById(authorId));
            sessionRequestContent.setRequestAttribute("bookcatalogentity", bookCatalog);
            LOGGER.debug("after deleteing: " + bookCatalog.getAuthors());

            authors = BookCatalogService.getAllAuthors();
            sessionRequestContent.setRequestAttribute("authors", authors);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
