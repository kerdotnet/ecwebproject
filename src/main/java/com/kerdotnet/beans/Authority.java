package com.kerdotnet.beans;
import javax.ejb.NoSuchEntityException;

/**
 * Class define user right model
 * Yevhen Ivanov
 */

public enum Authority {
    USER(1),
    ADMINISTRATOR(2);

    private int authorityId;

    Authority(int authorityId) {
        this.authorityId = authorityId;
    }

    public static Authority getById(int id){
        switch (id){
            case 1:
                return USER;
            case 2:
                return ADMINISTRATOR;
            default:
                return null;
        }
    }

    public int getAuthorityId(){
        return authorityId;
    }
}
