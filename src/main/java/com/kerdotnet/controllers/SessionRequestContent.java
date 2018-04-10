package com.kerdotnet.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Session Request content
 * TODO: implement to not send parameters to business logic
 * Yevhen Ivanov; 2018-04-09
 */

public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public void extractValues(HttpServletRequest request){

    }

    public void insertAttributes(HttpServletRequest request){

    }
}
