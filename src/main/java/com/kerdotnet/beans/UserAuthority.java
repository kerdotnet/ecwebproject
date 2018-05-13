package com.kerdotnet.beans;

/**
 * User Authority java DTO class
 *  Yevhen Ivanov, 2018-04-10
 */

public class UserAuthority extends Entity {
    private static final long serialVersionUID = 1L;

    private int userId;
    private int authorityId;

    public UserAuthority() {
    }

    public UserAuthority(int userId, int authorityId) {
        this.userId = userId;
        this.authorityId = authorityId;
    }

    public UserAuthority(int id, int userId, int authorityId) {
        this.setId(id);
        this.userId = userId;
        this.authorityId = authorityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuthority userAuthority = (UserAuthority) o;

        return getId() == userAuthority.getId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId();
        return result;
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
                "Id=" + getId() +
                ", userId=" + userId +
                ", authorityId='" + authorityId + '\'' +
                '}';
    }
}
