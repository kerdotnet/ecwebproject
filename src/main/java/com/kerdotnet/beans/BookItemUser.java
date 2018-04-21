package com.kerdotnet.beans;

import java.util.Date;

public class BookItemUser extends Entity {
    private static final long serialVersionUID = 1L;

    private int bookItemId;
    private int userId;
    private Date date;
    private boolean enabled;

    public BookItemUser() {
    }

    public BookItemUser(int bookItemId, int userId,
                        Date date, boolean enabled) {
        this.bookItemId = bookItemId;
        this.userId = userId;
        this.date = date;
        this.enabled = enabled;
    }

    public BookItemUser(int id, int bookItemId, int userId,
                        Date date, boolean enabled) {
        this.setId(id);
        this.bookItemId = bookItemId;
        this.userId = userId;
        this.date = date;
        this.enabled = enabled;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(int bookItemId) {
        this.bookItemId = bookItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "BookItemUser{" +
                "Id=" + getId() +
                "bookItemId=" + bookItemId +
                ", userId=" + userId +
                ", date=" + date +
                ", enabled=" + enabled +
                '}';
    }
}
