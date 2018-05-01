package com.kerdotnet.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementation of Message Messenger
 * Yevhen Ivanov; 2018-04-09
 */

public class MessageManager {
    private static ResourceBundle resourceBundle =
            ResourceBundle.getBundle("messages", Locale.getDefault());

    private MessageManager(){
    }

    public static void ChangeLocale(){
        resourceBundle =
                ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
