package com.kerdotnet.command;

import com.kerdotnet.beans.User;
import com.kerdotnet.dao.UserDAO;
import com.kerdotnet.dao.factory.DAOEnum;
import com.kerdotnet.dao.factory.DAOManager;
import com.kerdotnet.logic.LoginLogic;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

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
            User user = new User();
            user.setMobile("testMob");
            user.setLastName("lastName");
            user.setFirstName("firstName");
            user.setEmail("email@gmail");
            user.setPassword("pass");
            user.setUsername("uname");
            userDAO.create(user);
            System.out.println("user id = " + user.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String page = null;
        String login= request.getParameter(PARAM_NAME_LOGIN);
        String passwrod = request.getParameter(PARAM_NAME_PASSWORD);

        if (LoginLogic.checkLogin(login, passwrod)){
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
