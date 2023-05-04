package com.mhl.mycompanybackend.pojo;

import java.util.List;

public class JwtResponse {

    private final String token;
    private final String type = "Bearer";
    private Long id;
    private final String username;
    private final String email;
    private List<String> roles;


    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}

