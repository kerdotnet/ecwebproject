package com.kerdotnet.resource;

import java.util.ResourceBundle;

/**
 * Implementation of Configuration Messenger
 * Yevhen Ivanov; 2018-04-09
 */

public class ConfigurationManager {
    private final static ResourceBundle resourceBundle =
            ResourceBundle.getBundle("config");
    private ConfigurationManager(){
    }

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
