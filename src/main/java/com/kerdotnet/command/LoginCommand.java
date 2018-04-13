package com.kerdotnet.command;

import com.kerdotnet.beans.User;
import com.kerdotnet.dao.SQLImplementation.UserDAOImpl;
import com.kerdotnet.dao.UserDAO;
import com.kerdotnet.dao.factory.DAOEnum;
import com.kerdotnet.dao.factory.DAOManager;
import com.kerdotnet.logic.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of Login command
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginCommand implements ActionCommand{

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {


        //testing DAO
        //delete these code
        DAOManager daoManager = DAOManager.getInstance();
        UserDAO userDAO = null;
        try {
            userDAO = (UserDAO) daoManager.getDAO(DAOEnum.USER);
            List<User> users = userDAO.findAll();
            for (User user :
                    users) {
                System.out.println(user);
            }

            System.out.println(userDAO.findEntity(1));
            System.out.println(userDAO.findEntity(1));
            System.out.println(userDAO.findEntity(2));
            System.out.println(userDAO.findEntity(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String page = null;
        String login= request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        if (LoginLogic.checkLogin(login, password)){
            request.setAttribute("user", login);
            page = ConfigurationManager.getProperty("path.page.main");
            LOGGER.info("Login accomplished successfully, return page: " +
                    page);
        } else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
            LOGGER.info("Error login, return page: " +
                    page);
        }
        return page;
    }
}
