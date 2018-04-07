package com.kerdotnet.beans;

import java.io.Serializable;
import java.util.List;

/**
 *  User java bean class
 *  Yevhen Ivanov, 2018-04-01
 */

public class User extends Entity{
    private String username;
    private String password;
    private String emailId;
    private String firstName;
    private String lastName;
    private String mobile;
    private boolean enabled;
    private List<String> authorities;

    public User(String username, String password, String emailId,
                String firstName, String lastName,
                String mobile, boolean enabled, List<String> authorities) {
        this.username = username;
        this.password = password;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile  = mobile;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
