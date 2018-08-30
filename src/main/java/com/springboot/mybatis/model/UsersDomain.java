package com.springboot.mybatis.model;

import com.springboot.security.model.Authority;

import java.util.Date;

public class UsersDomain {

    private String email;
    private String password;
    private boolean enabled;
    private String userName;
    private String authority;
    private Date userRegTime;

    public UsersDomain build(String email,String password,String userName){
        this.setEmail(email);
        this.setPassword(password);
        this.setUserName(userName);
        this.setAuthority(Authority.GUESTS);
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getUserRegTime() {
        return userRegTime;
    }

    public void setUserRegTime(Date userRegTime) {
        this.userRegTime = userRegTime;
    }
}
