package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.exceptions.DAOSystemException;

import javax.ejb.NoSuchEntityException;

public class BookItemEnricher implements IEnricher<BookItem> {
    private final IBookItemUserDAO bookItemUserDAO;

    public BookItemEnricher(IBookItemUserDAO bookItemUserDAO) {
        this.bookItemUserDAO = bookItemUserDAO;
    }

    @Override
    public void enrich(BookItem record) throws DAOSystemException, NoSuchEntityException {
        BookItemUser bookItemUser = bookItemUserDAO.findEntity(record.getId());
        record.setBookItemUser(bookItemUser);
    }
}
