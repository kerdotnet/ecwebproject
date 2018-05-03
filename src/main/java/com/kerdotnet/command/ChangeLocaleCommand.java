package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;
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

    private final static String PARAM_LOCALE="language";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;
        page = ConfigurationManager.getProperty("path.page.login");
        String currentLocale = sessionRequestContent.getRequestParameter(PARAM_LOCALE);

        Locale.setDefault(new Locale(currentLocale));
        MessageManager.ChangeLocale();
        LOGGER.debug(currentLocale);

        return page;
    }
}
