package com.kerdotnet.command.bookcatalog;

import com.kerdotnet.beans.Author;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.IBookCatalogService;
import com.kerdotnet.service.factory.ServiceFactory;
import com.kerdotnet.service.serviceimplementation.BookCatalogServiceImpl;
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

    public EditBookCatalogAuthorsCommand() {
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

    public EditBookCatalogAuthorsCommand(IBookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        page = ConfigurationManager.getProperty("path.page.bookcatalogauthors");

        List<Author> authors;

        try {
            authors = bookCatalogService.getAllAuthors();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        sessionRequestContent.setRequestAttribute("authors", authors);

        return page;
    }
}
