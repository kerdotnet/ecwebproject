package com.kerdotnet.beans;

import java.util.List;

/**
 *  User java bean class
 *  Yevhen Ivanov, 2018-04-01
 */

public class User extends Entity{
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String mobile;
    private boolean enabled;
    private List<UserAuthority> authorities;

    public User() {
    }

    public User(String username, String password, String emailId,
                String firstName, String lastName,
                String mobile, boolean enabled, List<UserAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.email = emailId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }
}
