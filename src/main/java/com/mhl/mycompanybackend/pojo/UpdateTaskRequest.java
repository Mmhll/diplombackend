package com.mhl.mycompanybackend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UpdateTaskRequest {
    Long task_id;
    String name;
    String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date deadline;

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
