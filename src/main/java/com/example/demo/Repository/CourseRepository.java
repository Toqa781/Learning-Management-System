package com.example.demo.Repository;

import com.example.demo.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByCreator_UserId(String userId);
}
