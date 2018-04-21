package com.kerdotnet.beans;

import java.util.Date;

public class Transaction extends Entity{
    private static final long serialVersionUID = 1L;

    private Date date;
    private int bookItemId;
    private int userId;
    private String bookShelfAdress;
    private String action;
    private boolean enabled;

    public Transaction() {
    }

    public Transaction(Date date, int bookItemId, int userId,
                       String bookShelfAdress, String action, boolean enabled) {
        this.date = date;
        this.bookItemId = bookItemId;
        this.userId = userId;
        this.bookShelfAdress = bookShelfAdress;
        this.action = action;
        this.enabled = enabled;
    }

    public Transaction(int id, Date date, int bookItemId, int userId,
                       String bookShelfAdress, String action, boolean enabled) {
        this.setId(id);
        this.date = date;
        this.bookItemId = bookItemId;
        this.userId = userId;
        this.bookShelfAdress = bookShelfAdress;
        this.action = action;
        this.enabled = enabled;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getBookShelfAdress() {
        return bookShelfAdress;
    }

    public void setBookShelfAdress(String bookShelfAdress) {
        this.bookShelfAdress = bookShelfAdress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "Id=" + getId() +
                "date=" + date +
                ", bookItemId=" + bookItemId +
                ", userId=" + userId +
                ", bookShelfAdress='" + bookShelfAdress + '\'' +
                ", action='" + action + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
