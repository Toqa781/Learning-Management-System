package com.example.demo.Controller;

import com.example.demo.Model.CreateUserRequest;
import com.example.demo.Service.Authentication.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.Users.Admin;
import com.example.demo.Model.Users.User;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String adminId = authentication.getName();
            Admin admin = (Admin) userService.loadUserByUsername(adminId);

            if (!"ADMIN".equals(admin.getRole())) {
                return ResponseEntity.status(403).body("Only admins can create users.");
            }

            User newUser = new User();
            newUser.setUserId(request.getUserId());
            newUser.setPassword(request.getUserPassword());
            newUser.setRole(request.getUserRole());

            User createdUser = userService.createUser(admin, newUser);
            return ResponseEntity.ok("User created successfully: " + createdUser.getUserId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

