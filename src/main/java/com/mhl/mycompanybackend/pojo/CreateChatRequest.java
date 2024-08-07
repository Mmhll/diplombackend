package com.mhl.mycompanybackend.pojo;

import java.util.ArrayList;
import java.util.List;

public class CreateChatRequest {
    private String name;
    List<Long> members = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getMembers() {
        return members;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }
}
