package com.mhl.mycompanybackend.pojo;

public class SignupRequest {

    private String email;
    private String firstname;
    private String lastname;
    private String middlename;
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SignupRequest(String email, String firstname, String lastname, String middlename, String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.password = password;
    }

    public SignupRequest() {
    }
}

