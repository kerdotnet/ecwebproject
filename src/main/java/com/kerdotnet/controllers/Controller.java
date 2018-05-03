package com.kerdotnet.controllers;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.command.factory.ActionFactory;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.resource.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller processes http requests
 * Yevhen Ivanov, 2018-04-02
 */

@WebServlet(urlPatterns = "/controller",
        loadOnStartup = 0)
public class Controller extends HttpServlet{

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException, ServletException {
        String page = null;

        ActionFactory client = new ActionFactory();
        SessionRequestContent sessionRequestContent = new SessionRequestContent(request, response);
        IActionCommand command = client.defineCommand(sessionRequestContent);

        page = command.execute(sessionRequestContent);

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
}
