package com.kerdotnet.utility;

import java.util.regex.Pattern;

/**
 * This class process all validations with the data
 * Yevhen Ivanov, 2018-04-22
 */
public class Validator {
    private static final Pattern LOGIN_PATTERN =
            Pattern.compile("[A-Za-z0-9_]+");
    private static final Pattern PASS_PATTERN =
            Pattern.compile("\\A(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+*=!])(?=\\S+$).{8,}\\z");
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[а-яА-ЯіІїЇёЁa-zA-Z0-9\\s]+$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^((.+)?([0-9]{1})?[0-9]{1}-?)?[0-9]{3}-?[0-9]{3}-?[0-9]{4}$");


    /**
     * simple validation that password and confirmation password are equal
     * @param password
     * @param confirmPassword
     * @return
     */
    public static boolean validatePasswordsOnEquality(String password, String confirmPassword) {
        if ((password == null)|| "".equals(password)||(confirmPassword == null)|| "".equals(confirmPassword))
            return false;

        if (password.equals(confirmPassword))
            return true;
        return false;
    }

    /**
     * validate login on correct input
     * @param login
     * @return
     */
    public static boolean validateLogin(String login){
        return (login != null) && LOGIN_PATTERN.matcher(login).matches();
    }

    /**
     * validate password on correct input
     * @param pass
     * @return
     */
    public static boolean validatePassword(String pass){
        return (pass != null) && PASS_PATTERN.matcher(pass).matches();
    }

    /**
     * validate name or surname on correct input
     * @param name
     * @return
     */
    public static boolean validateName(String name){
        return (name != null) && NAME_PATTERN.matcher(name).matches();
    }

    /**
     * validate email on correct input
     * @param email
     * @return
     */
    public static boolean validateEmail(String email){
        return (email != null) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * validate mobile on correct input
     * @param mobile
     * @return
     */
    public static boolean validateMobile(String mobile){
        return (mobile != null) && MOBILE_PATTERN.matcher(mobile).matches();
    }
}
