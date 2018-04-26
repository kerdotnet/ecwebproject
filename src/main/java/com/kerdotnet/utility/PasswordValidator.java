package com.kerdotnet.utility;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Validate password with hash
 * and create a new hash for password
 * Yevhen Ivanov, 2018-04-16
 */

public class PasswordValidator {
    private final static int workload = 4;

    /**
     * This method is used to generate a string representing an account password
     * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
     * hash string of length=60
     * @param password_plaintext
     * @return
     */
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

    /**
     * validate password against the hash
     * @param password_plaintext
     * @param stored_hash
     * @return
     */
    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }
}
