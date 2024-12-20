package com.example.demo.Controller.Assessments;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Course;
import com.example.demo.Service.Assessments.AssignmentService;
import com.example.demo.Service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/assignments")
public class AssignmentController {

    private AssignmentService assignmentService;

    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@PathVariable String courseId , @RequestBody Assignment assignment) {
        Course course = courseService.getCourseById(courseId);
        if(course == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }

        assignmentService.saveAssignment(assignment);

        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);

    }

}
