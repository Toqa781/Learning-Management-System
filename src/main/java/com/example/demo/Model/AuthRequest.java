package com.example.demo.Model;


//DTO

public class AuthRequest {
    private String userId;
    private String password;

    public AuthRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }


    public void setUserId(String username) {
        this.userId = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }
}