package com.mhl.mycompanybackend.pojo;

public class SignupRequest {

    private String email;
    private String firstname;
    private String lastname;
    private String middlename;
    private String password;
    private String phone_number;

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SignupRequest(String email, String firstname, String lastname, String middlename, String password, String phoneNumber) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.password = password;
        this.phone_number = phoneNumber;
    }

    public SignupRequest() {
    }
}

