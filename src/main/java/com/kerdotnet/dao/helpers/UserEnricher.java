package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.User;
import com.kerdotnet.beans.UserAuthority;
import com.kerdotnet.dao.UserAuthorityDAO;
import com.kerdotnet.exceptions.DAOSystemException;

import javax.ejb.NoSuchEntityException;
import java.util.List;

public class UserEnricher implements Enricher<User> {
    private final UserAuthorityDAO userAuthorityDAO;

    public UserEnricher(UserAuthorityDAO userAuthorityDAO) {
        this.userAuthorityDAO = userAuthorityDAO;
    }

    @Override
    public void enrich(User record) throws DAOSystemException, NoSuchEntityException {
        List<UserAuthority> UserAuthority =  userAuthorityDAO.findAllByUserId(record.getId());
        record.setAuthorities(UserAuthority);
    }
}
