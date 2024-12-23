package com.example.demo.Model.Users;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Inheritance strategy
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)  // Discriminator column
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

    public void setName(String name) {
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
