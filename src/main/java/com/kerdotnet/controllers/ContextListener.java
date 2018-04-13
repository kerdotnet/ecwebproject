package com.kerdotnet.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Context Listener
 * init logger for the application
 * Yevhen Ivanov, 2018-04-01
 */

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

    static final Logger LOGGER = Logger.getLogger(Controller.class);

    private void initLogger(ServletContext context){
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        initLogger(context);

    }
}
