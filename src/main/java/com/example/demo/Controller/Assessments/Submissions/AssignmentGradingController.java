package com.example.demo.Controller.Assessments.Submissions;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Grading;
import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import com.example.demo.Model.Course;
import com.example.demo.Service.Assessments.AssignmentService;
import com.example.demo.Service.Assessments.Submissions.AssignmentSubmissionService;
import com.example.demo.Service.Authentication.JWTService;
import com.example.demo.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/assignments")
public class AssignmentGradingController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @PostMapping("/gradeAssignments")
    public ResponseEntity<?> gradeAssignments(@PathVariable String courseId, @RequestBody List<Grading> grades) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }

        for (Grading grade : grades) {
            Assignment assignment = assignmentService.getAssignment(grade.getAssessmentId());
            if (assignment == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assignment " + grade.getAssessmentId() + " doesn't exist.");
            }

            AssignmentSubmission studentAssignment = assignmentSubmissionService.getStudentAssignmentSubmission(grade.getStudentId(), grade.getAssessmentId());

            if(studentAssignment==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student " + grade.getStudentId() + " didn't submit Assignment " + grade.getAssessmentId());
            }
            studentAssignment.setGrade(grade.getGrade());
            studentAssignment.setGraded(true);

            assignmentSubmissionService.saveStudentSubmission(studentAssignment);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("All Assignments are Graded");
    }


    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/{assessmentId}/getGrade")
    public ResponseEntity<?> getAssignmentGrade(@PathVariable String courseId , @PathVariable long assessmentId , @RequestHeader("Authorization") String tokenHeader) {

        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
        String studentId;
        try {
            studentId = jwtService.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }

        if(!courseService.checkStudentEnrollment(studentId,courseId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You have not enrolled in this Course");
        }

        Assignment assignment = assignmentService.getAssignment(assessmentId);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assignment doesn't exist.");
        }

        AssignmentSubmission studentAssignment = assignmentSubmissionService.getStudentAssignmentSubmission(studentId, assessmentId);
        if(studentAssignment==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You  didn't submit Assignment " + assessmentId + "or Late submission (Not Accepted).");
        }

        if(!studentAssignment.isGraded()){
            return ResponseEntity.status(HttpStatus.OK).body("Your Assignment has not been Graded.");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentAssignment.getGrade());
    }
}
