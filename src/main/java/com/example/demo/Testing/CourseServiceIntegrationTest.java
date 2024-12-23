package com.example.demo.Testing;

import com.example.demo.Model.Users.Instructor;
import com.example.demo.Repository.CourseRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.Model.Course;


@SpringBootTest
public class CourseServiceIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateAndRetrieveCourse() {
        // Clear repository
        courseRepository.deleteAll();

        Instructor instructor = new Instructor();
        instructor.setUserId("instructor001");
        instructor.setName("Dr. Toqa");
        userRepository.save(instructor);

        courseService.createCourse("C001", "Java Basics", "Introduction to Java", instructor);

        Course retrievedCourse = courseService.getCourseById("C001");
        assertNotNull(retrievedCourse, "Course should not be null");
        assertEquals("C001", retrievedCourse.getCourseId());
        assertEquals("Java Basics", retrievedCourse.getCourseName());
        assertEquals("Introduction to Java", retrievedCourse.getCourseDescription());
        assertEquals("instructor001", retrievedCourse.getCreator().getUserId());
    }
}
