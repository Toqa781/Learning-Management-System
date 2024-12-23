package com.example.demo.Controller;

import com.example.demo.Service.Authentication.UserService;
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


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    UserController(UserService service, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.service = service;

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User userInfo) {
        try {
            service.register(userInfo);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserId(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUserId());
            return ResponseEntity.ok(token);
        }
         else{
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

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }


}