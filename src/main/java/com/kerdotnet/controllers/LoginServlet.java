package com.kerdotnet.controllers;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * initial login servlet
 * modify or delete this LoginServlet
 * Yevhen Ivanov, 2018-04-02
 */

public class LoginServlet extends GenericServlet{

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //change the code hear
        servletResponse.setContentType("text/html");
        PrintWriter out = servletResponse.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Hello world (ECWEBPROJECT)</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}
