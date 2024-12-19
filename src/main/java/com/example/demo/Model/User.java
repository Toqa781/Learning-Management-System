package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")

    private String userId;
    @Column(name = "name")

    private String name;
    @Column(name = "email")

    private String email;
    @Column(name = "password")

    private String password;
    @Column(name = "role")

    private String role;

    public User(String role, String password, String email, String name, String userId) {
        this.role = role;
        this.password = password;
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
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
