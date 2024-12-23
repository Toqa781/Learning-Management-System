package com.example.demo.Testing;

import com.example.demo.Model.Course;
import com.example.demo.Model.Notifications;
import com.example.demo.Model.Users.Instructor;
import com.example.demo.Repository.CourseRepository;
import com.example.demo.Repository.NotificationsRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.NotificationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class NotificationsServiceTest {
    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void CreateNotificationtest() {
        notificationsRepository.deleteAll();
        userRepository.deleteAll();
        courseRepository.deleteAll();

        Instructor instructor = new Instructor();
        instructor.setUserId("instructor001");
        instructor.setName("habiba");
        userRepository.save(instructor);

        Course course = new Course();
        course.setCourseId("C001");
        course.setCourseName("Java Basics");
        course.setCourseDescription("Introduction to Java");
        course.setCreator(instructor);
        courseRepository.save(course);

        Notifications notification = notificationsService.createNotification(
                instructor,
                "You have a new enrollment in Java Basics",
                "student_enrollment",
                course
        );

        assertNotNull(notification, "Notification should not be null");
        assertEquals("You have a new enrollment in Java Basics", notification.getMessage());
        assertEquals("student_enrollment", notification.getTypeOfNotification());
        assertEquals("Java Basics", notification.getCourse().getCourseName());
        assertEquals("instructor001", notification.getUser().getUserId());
    }




}
