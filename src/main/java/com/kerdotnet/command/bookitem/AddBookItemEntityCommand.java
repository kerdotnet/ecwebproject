package com.kerdotnet.command.bookitem;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Adding of a new BookItem Entity
 * Yevhen Ivanov; 2018-04-30
 */
public class AddBookItemEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddBookItemEntityCommand.class);

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        page = ConfigurationManager.getProperty("path.page.bookitementity");

        return page;
    }
}
