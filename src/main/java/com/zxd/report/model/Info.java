package com.zxd.report.model;

import java.io.Serializable;

public class Info implements Serializable {
    private static final long serialVersionUID=989898913232L;
    private String username;
    private String password;
    private String perm;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    @Override
    public String toString() {
        return "Info{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", perm='" + perm + '\'' +
                '}';
    }
}
