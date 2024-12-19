package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.User;
import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String id);
    Optional<User> findById(String id);
}