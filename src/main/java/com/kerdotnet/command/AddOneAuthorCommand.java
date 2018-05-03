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
 * Add one author to BookCatalog
 * Yevhen Ivanov; 2018-04-25
 */
public class AddOneAuthorCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddOneAuthorCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

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
            authors = BookCatalogService.getAllAuthors();
            sessionRequestContent.setRequestAttribute("authors", authors);
            LOGGER.debug("authors: " + authors);

            if (authorId != 0) {
                bookCatalog.addOneAuthor(BookCatalogService.findAuthorById(authorId));
            }

            sessionRequestContent.setRequestAttribute("bookcatalogentity", bookCatalog);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        return page;
    }
}
