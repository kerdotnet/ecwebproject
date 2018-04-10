package com.kerdotnet.resource;

import java.util.ResourceBundle;

/**
 * Implementation of Message Messenger
 * TODO: add localization
 * Yevhen Ivanov; 2018-04-09
 */

public class MessageManager {
    private final static ResourceBundle resourceBundle =
            ResourceBundle.getBundle("messages");

    private MessageManager(){
    }

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
