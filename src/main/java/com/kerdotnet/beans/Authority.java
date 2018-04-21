package com.kerdotnet.beans;

/**
 * Class define user right model
 * Yevhen Ivanov
 */

public class Authority extends Entity{

    private static final long serialVersionUID = 1L;

    private String name;
    private boolean isUser;
    private boolean isAdministrator;

    public Authority() {
    }

    public Authority(String name, boolean isUser, boolean isAdministrator) {
        this.name = name;
        this.isUser = isUser;
        this.isAdministrator = isAdministrator;
    }

    public Authority(int id, String name, boolean isUser, boolean isAdministrator) {
        this.setId(id);
        this.name = name;
        this.isUser = isUser;
        this.isAdministrator = isAdministrator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "Id=" + getId() +
                "name='" + name + '\'' +
                ", isUser=" + isUser +
                ", isAdministrator=" + isAdministrator +
                '}';
    }
}
