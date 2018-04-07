package com.kerdotnet.dao;

import com.kerdotnet.beans.User;
import java.util.List;

/**
 * implements UserDao for  managing the persistent state of the User object
 * Yevhen Ivanov, 2018-04-01
 */

public interface UserDao {

    /**
     * create a new user
     * @return
     */
    User create();

    /**
     * read a User by username
     * @param username
     * @return
     */
    User read(String username);

    /**
     * update a user information
     * @param user
     */
    void update(User user);

    /**
     * delete user interface
     * @param user
     */
    void delete(User user);

    /**
     * return all users
     * @return
     */
    List<User> getAllUsers();
}
