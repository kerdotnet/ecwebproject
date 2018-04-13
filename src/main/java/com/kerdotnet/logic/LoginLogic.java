package com.kerdotnet.logic;

/**
 * Implementation of Login command
 * Yevhen Ivanov; 2018-04-09
 */

public class LoginLogic {
    public static boolean checkLogin(String login, String passwrod) {
        //replace this functionality
        if (login.equals("kerdotnet")&&
                passwrod.equals("disappear")){
            return true;
        }
        return false;
    }

    public static boolean logout() {
        return false;
    }
}
