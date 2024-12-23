package com.example.demo.Repository;

import com.example.demo.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByCreator_UserId(String userId);
}
