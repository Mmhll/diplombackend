package com.mhl.mycompanybackend.pojo;

import com.mhl.mycompanybackend.models.Users;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {
    private String name;
    List<Users> members = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Users> getMembers() {
        return members;
    }

    public void setMembers(List<Users> members) {
        this.members = members;
    }
}
