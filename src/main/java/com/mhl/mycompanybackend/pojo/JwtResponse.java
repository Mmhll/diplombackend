package com.mhl.mycompanybackend.pojo;

import java.util.List;

public class JwtResponse {

    private final String token;
    private final String type = "Bearer";


    public JwtResponse(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}

