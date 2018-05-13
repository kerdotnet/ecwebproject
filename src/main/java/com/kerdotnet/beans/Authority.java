package com.kerdotnet.beans;

/**
 * Class define user right model
 * Yevhen Ivanov, 2018-04-10
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return getId() == authority.getId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId();
        return result;
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
