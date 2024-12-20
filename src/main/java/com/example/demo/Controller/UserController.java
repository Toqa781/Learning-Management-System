package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.AuthRequest;
import com.example.demo.Model.Users.User;
import com.example.demo.Service.Authentication.JWTService;
import com.example.demo.Service.Authentication.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    UserController(UserService service, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User userInfo) {
        service.register(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(authRequest.getUserId(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUserId());
            return ResponseEntity.ok(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody User user,
                                                @RequestParam String newEmail,
                                                @RequestParam String newName) {
        try {
            service.manageProfile( user,newEmail, newName);
            return ResponseEntity.ok("Profile updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}