package com.kerdotnet.command.factory;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.command.EmptyCommand;
import com.kerdotnet.command.client.CommandEnum;
import com.kerdotnet.controllers.SessionRequestContent;
import com.kerdotnet.dao.transaction.ITransactionManager;
import com.kerdotnet.dao.transaction.TransactionManagerImpl;
import com.kerdotnet.resource.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.TransactionManager;

/**
 * Action Factory class
 * produce commands for the applcation
 * Yevhen Ivanov; 2018-04-09
 */
public class ActionFactory {
    static final Logger LOGGER = LoggerFactory.getLogger(ActionFactory.class);

    public IActionCommand defineCommand(SessionRequestContent sessionRequestContent){
        IActionCommand current = new EmptyCommand();

        String action = sessionRequestContent.getRequestParameter("command");
        LOGGER.debug("Action: " + action);

        if (action == null || action.isEmpty()){
            return current;
        }

        try {
            CommandEnum currentEnum =
                    CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
            LOGGER.debug("current command: " + current);
        } catch (IllegalArgumentException e){
            LOGGER.error("Unexpected error", e);
            sessionRequestContent.setRequestAttribute("wrongAction", action +
                    MessageManager.getProperty("message.wrongaction"));
        }
        return current;
    }
}
