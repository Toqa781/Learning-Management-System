package com.example.demo.Model;
public class User {
    private String userId;
    private String username;
    private String email;
    private String password;
    private String role;

    public User(String role, String password, String email, String userName, String userId) {
        this.role = role;
        this.password = password;
        this.email = email;
        this.username = userName;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}