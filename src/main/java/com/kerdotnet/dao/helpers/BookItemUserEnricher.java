package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.beans.User;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.exceptions.DAOSystemException;

import javax.ejb.NoSuchEntityException;

public class BookItemUserEnricher implements IEnricher<BookItemUser> {
    private final IUserDAO userDAO;

    public BookItemUserEnricher(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void enrich(BookItemUser record) throws DAOSystemException, NoSuchEntityException {
        User user = userDAO.findEntity(record.getUserId());
        record.setUser(user);
    }
}
