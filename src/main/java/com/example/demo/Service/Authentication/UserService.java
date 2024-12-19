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

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findById(id);
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + id));
    }

    public void register(User user) {
        if (repository.existsById(user.getUserId())) {
            throw new IllegalArgumentException("User ID already exists");
        }

        if (user instanceof Student) {
            user.setRole("STUDENT");
        } else if (user instanceof Instructor) {
            user.setRole("INSTRUCTOR");
        } else if (user instanceof Admin) {
            user.setRole("ADMIN");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    public boolean login(String userId, String rawPassword) {
        Optional<User> userOptional = repository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return encoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    public void manageProfile() {

    }

    public void CreateUser(Admin admin) {

    }


}

