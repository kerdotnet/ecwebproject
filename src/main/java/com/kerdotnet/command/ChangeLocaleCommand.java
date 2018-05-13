package com.kerdotnet.command;

import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.Locale;

/**
 * Change locale
 * Yevhen Ivanov; 2018-05-01
 */
public class ChangeLocaleCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeLocaleCommand.class);

    private final static String PARAM_LOCALE = "language";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        String currentLocale = sessionRequestContent.getRequestParameter(PARAM_LOCALE);

        String login = (String) sessionRequestContent.getSessionAttribute("user");
        LOGGER.debug("current login: " + login);


        if (login == null) {
            page = ConfigurationManager.getProperty("path.page.login");
        } else {
            page = ConfigurationManager.getProperty("path.page.main");
        }

        Locale.setDefault(new Locale(currentLocale));
        MessageManager.ChangeLocale();

        LOGGER.debug("Locale was change. New locale is " + currentLocale);

        return page;
    }
}
