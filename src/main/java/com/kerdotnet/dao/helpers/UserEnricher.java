package com.kerdotnet.dao.helpers;

import com.kerdotnet.entity.User;
import com.kerdotnet.entity.UserAuthority;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.exceptions.DAOSystemException;

import javax.ejb.NoSuchEntityException;
import java.util.List;

public class UserEnricher implements IEnricher<User> {
    private final IUserAuthorityDAO userAuthorityDAO;

    public UserEnricher(IUserAuthorityDAO userAuthorityDAO) {
        this.userAuthorityDAO = userAuthorityDAO;
    }

    @Override
    public void enrich(User record) throws DAOSystemException, NoSuchEntityException {
        List<UserAuthority> userAuthority =  userAuthorityDAO.findAllByUserId(record.getId());
        record.setAuthorities(userAuthority);
    }
}
