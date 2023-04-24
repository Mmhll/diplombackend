package com.mhl.mycompanybackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks") // Таблица tasks, хранящая задачи мобильного приложения
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
    private Users creator;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creation_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_of_update;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_id")
    private Users executor;
    private Long status;
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tasks_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> members = new ArrayList<>();

    public Tasks(String name, Users creator, Date creation_date, Date date_of_update, String description, Date deadline, Users executor, Long status, List<Users> members) {
        this.name = name;
        this.creator = creator;
        this.creation_date = creation_date;
        this.date_of_update = date_of_update;
        this.description = description;
        this.deadline = deadline;
        this.executor = executor;
        this.status = status;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getDate_of_update() {
        return date_of_update;
    }

    public void setDate_of_update(Date date_of_update) {
        this.date_of_update = date_of_update;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<Users> getMembers() {
        return members;
    }

    public void setMembers(List<Users> members) {
        this.members = members;
    }

    public Tasks() {
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}