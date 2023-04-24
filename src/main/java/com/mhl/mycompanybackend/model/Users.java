package com.mhl.mycompanybackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    @JsonIgnore
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Roles> roles = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserData userData;
    @OneToMany(mappedBy = "user")
    List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    List<Tasks> task_creator;

    @OneToMany(mappedBy = "executor")
    List<Tasks> task_executor;

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Tasks> getTask_creator() {
        return task_creator;
    }

    public void setTask_creator(List<Tasks> task_creator) {
        this.task_creator = task_creator;
    }

    public List<Tasks> getTask_executor() {
        return task_executor;
    }

    public void setTask_executor(List<Tasks> task_executor) {
        this.task_executor = task_executor;
    }

    public Users() {
    }

    public Users(String username, String email, String password, UserData userData) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userData = userData;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}

