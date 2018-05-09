package com.kerdotnet.service;

import com.kerdotnet.beans.User;
import com.kerdotnet.exceptions.NotUniqueUserEmailException;
import com.kerdotnet.exceptions.NotUniqueUserLoginException;
import com.kerdotnet.exceptions.ServiceException;

/**define login service Logic
 * Yevhen Ivanov, 2018-05-09
 */
public interface ILoginService extends IService{

    /**
     * Check if such credentials can be entered in the system
     * if yes return true
     *
     * @param login
     * @param passwrod
     * @return
     * @throws ServiceException
     */
    boolean checkLogin(String login, String passwrod) throws ServiceException;

    /**
     * return true if the user is Administrator
     * in other cases - false
     *
     * @param login
     * @return
     * @throws ServiceException
     */
    boolean checkAdministratorRole(String login) throws ServiceException;

    /**
     * create a new user in database or return an error
     *
     * @param user
     * @return
     * @throws ServiceException, NotUniqueUserLoginException, NotUniqueUserEmailException
     */
    boolean addUser(User user) throws ServiceException, NotUniqueUserLoginException, NotUniqueUserEmailException;
}
