package com.kerdotnet.command.factory;

import com.kerdotnet.command.ActionCommand;
import com.kerdotnet.command.EmptyCommand;
import com.kerdotnet.command.client.CommandEnum;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Action Factory class
 * produce commands for the applcation
 * Yevhen Ivanov; 2018-04-09
 */
public class ActionFactory {
    static final Logger LOGGER = Logger.getLogger(ActionFactory.class);

    public ActionCommand defineCommand(HttpServletRequest request){

        ActionCommand current = new EmptyCommand();

        String action = request.getParameter("command");
        LOGGER.info("Action: " + action);

        if (action == null || action.isEmpty()){
            return current;
        }

        try {
            CommandEnum currentEnum =
                    CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
            LOGGER.info("current command: " + current);
        } catch (IllegalArgumentException e){
            LOGGER.error("Unexpected error", e);
            request.setAttribute("wrongAction", action +
                    MessageManager.getProperty("message.wrongaction"));
        }
        return current;
    }
}
