package com.kerdotnet.command;

import com.kerdotnet.beans.Author;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.BookCatalogService;
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

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        page = ConfigurationManager.getProperty("path.page.bookcatalogauthors");

        List<Author> authors;

        try {
            authors = BookCatalogService.getAllAuthors();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        sessionRequestContent.setRequestAttribute("authors", authors);

        return page;
    }
}
