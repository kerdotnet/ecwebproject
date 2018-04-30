package com.kerdotnet.beans;

import java.time.LocalDateTime;

/**
 * java DTO class for links between Book Items and Users entities
 *  Yevhen Ivanov, 2018-04-21
 */

public class BookItemUser extends Entity {
    private static final long serialVersionUID = 1L;

    private int bookItemId;
    private int userId;
    private LocalDateTime date;
    private boolean enabled;
    private User user;

    public BookItemUser() {
    }

    public BookItemUser(int bookItemId, int userId,
                        LocalDateTime date, boolean enabled) {
        this.bookItemId = bookItemId;
        this.userId = userId;
        this.date = date;
        this.enabled = enabled;
    }

    public BookItemUser(int id, int bookItemId, int userId,
                        LocalDateTime date, boolean enabled) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BookItemUser{" +
                "Id=" + getId() +
                "bookItemId=" + bookItemId +
                ", userId=" + userId +
                ", date=" + date +
                ", enabled=" + enabled +
                ", user=" + user +
                '}';
    }
}
