package com.kerdotnet.controllers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.kerdotnet.command.ActionCommand;
import com.kerdotnet.command.factory.ActionFactory;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.apache.log4j.Logger;

/**
 * Controller processes http requests
 * Yevhen Ivanov, 2018-04-02
 */

@WebServlet("/controller")
public class Controller extends HttpServlet{

    static final Logger LOGGER = Logger.getLogger(Controller.class);
    protected DataSource dataSource;

    @Override
    public void init() throws ServletException {
        initDataSource();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
        LOGGER.info("doGet in Controller accomplished successfully");
    }

    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
        LOGGER.info("doPost in Controller accomplished successfully");
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException, ServletException {
        String page = null;

        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);

        page = command.execute(request);

        if (page != null){
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    MessageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }

    private void initDataSource(){
        dataSource = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/libraryDB");

            LOGGER.info("DataSource was successfully initialized");

        } catch (NamingException e) {
            LOGGER.error("Unexpected error", e);
        }
    }
}
