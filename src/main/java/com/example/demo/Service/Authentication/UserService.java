package com.example.demo.Service.Authentication;
import com.example.demo.Model.Users.Admin;
import com.example.demo.Model.Users.Instructor;
import com.example.demo.Model.Users.Student;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Users.User;
import com.example.demo.Repository.UserRepository;

import java.util.Optional;


// Not finished
@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
//    private final AuthenticationManager authenticationManager;
//    private final JWTService jwtService;



    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;

    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> userDetail = (repository.findById(id));
        return userDetail.map(UserInfoDetails::new)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found " + id));

    }

    public void register(User user) {
        if (repository.existsById(user.getUserId())) {
            throw new IllegalArgumentException("User ID already exists");
        }

        User newuser;

        if ("STUDENT".equals(user.getRole())) {
            newuser = new Student();
        } else if ("INSTRUCTOR".equals(user.getRole())) {
            newuser = new Instructor();
        } else if ("ADMIN".equals(user.getRole())) {
            newuser = new Admin();
        } else {
            newuser = new User();
        }
        newuser.setUserId(user.getUserId());
        newuser.setPassword(encoder.encode(user.getPassword()));
        newuser.setRole(user.getRole());
        newuser.setEmail(user.getEmail());
        newuser.setName(user.getName());

        repository.save(newuser);

    }



    public void manageProfile(User user,String newEmail ,String newName) {
        boolean userExist = repository.existsById(user.getUserId());

        if (userExist) {

            user.setEmail(newEmail);
            user.setName(newName);
            repository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User createUser(User newUser) {
        if (repository.existsById(newUser.getUserId())) {
            throw new IllegalArgumentException("User ID already exists.");
        }
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        repository.save(newUser);

        return newUser;
    }



}

