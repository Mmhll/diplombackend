package com.mhl.mycompanybackend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mhl.mycompanybackend.model.Users;

import java.util.Date;
import java.util.List;

public class TaskRequest {
    String task_name;
    Users creator;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creation_date;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    Users executor;
    List<Users> members;

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Users getExecutor() {
        return executor;
    }

    public void setExecutor(Users executor) {
        this.executor = executor;
    }

    public List<Users> getMembers() {
        return members;
    }

    public void setMembers(List<Users> members) {
        this.members = members;
    }
}
