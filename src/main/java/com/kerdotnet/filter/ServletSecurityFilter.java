package com.kerdotnet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security filter which checks that only
 * authorized users work with application
 * Yevhen Ivanov, 2018-04-22
 */

@WebFilter(urlPatterns = {"/controller"}, servletNames = {"MainServlet"})
public class ServletSecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        HttpSession session = servletRequest.getSession();
        ClientType type = (ClientType) session.getAttribute("userType");
        if (type == null || type == ClientType.GUEST) {
            type = ClientType.GUEST;
            session.setAttribute("userType", type);
            String action = servletRequest.getParameter("command");
            if (action != null && ("registration".equals(action)
                    ||"login".equals(action)
                    ||"adduser".equals(action))) {
                chain.doFilter(request, response);
            } else {
                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/login.jsp");
                dispatcher.forward(servletRequest, servletResponse);
            }
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
