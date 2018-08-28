package com.example.anti2110.wantedjob.model;

public class User {

    private String email;
    private String password;
    private String isStaff;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.isStaff = "false";
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

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }
}
