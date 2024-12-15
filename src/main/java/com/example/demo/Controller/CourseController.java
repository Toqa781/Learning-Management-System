package com.example.demo.Controller;
import com.example.demo.Model.*;
import com.example.demo.Service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin

@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService=new CourseService();

    @PostMapping
    public void createCourse(@RequestBody Course course){
        courseService.createCourse(course.getCourseId(),course.getCourseName(),course.getCourseDescreption());
    }

    @GetMapping
    public List<Course>getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable String courseId){
        return courseService.getCourseById(courseId);
    }

    @PostMapping("/{courseId}/lessons")
    public void addLesson(@PathVariable String courseId,@RequestBody Lesson lesson){
        courseService.addLessonToCourse(courseId,lesson);
    }
}