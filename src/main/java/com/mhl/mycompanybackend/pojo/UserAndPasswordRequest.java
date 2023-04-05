package com.mhl.mycompanybackend.pojo;

public class UserAndPasswordRequest {

    private String username;
    private String password;

    public UserAndPasswordRequest() {
    }

    public UserAndPasswordRequest(String username, String password) {
        this.username = username;
        this.password = password;
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

}
