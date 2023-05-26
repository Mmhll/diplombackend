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
    @JsonManagedReference(value = "task-creator")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id")
    private Users creator;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creation_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_of_update;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    private String status;

    @JsonManagedReference(value = "task-executor")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "executor_id")
    private Users executor;
    @Transient
    @JsonManagedReference(value = "task-members")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "tasks_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> members = new ArrayList<>();

    public Tasks(String name, Users creator, Date creation_date, Date date_of_update, String description, Date deadline, Users executor, String status) {
        this.name = name;
        this.creator = creator;
        this.creation_date = creation_date;
        this.date_of_update = date_of_update;
        this.description = description;
        this.deadline = deadline;
        this.executor = executor;
        this.status = status;
    }

    public Tasks(String name, Users creator, Date creation_date, String description, Date deadline, Users executor, String status, List<Users> members) {
        this.name = name;
        this.creator = creator;
        this.creation_date = creation_date;
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
    
    public String getStatus() {
        return status;   
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Tasks() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getCreator() {
        return creator;
    }

    public Users getExecutor() {
        return executor;
    }

    public List<Users> getMembers() {
        return members;
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
}
