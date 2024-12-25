package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Course;
import com.example.demo.Model.Users.Student;
import com.example.demo.Repository.Assesments.AssignmentRepository;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.NotificationsService;
import com.example.demo.Service.StudentNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private CourseService courseService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private StudentNotificationsService studentnotificationsService;

    public void saveAssignment(Assignment assignment){
        assignmentRepository.save(assignment);
        // Notify students about the new assignment
        if (assignment.getCourseId() != null) {
            Course course = courseService.getCourseById(assignment.getCourseId());
            if (course != null) {
                List<Student> enrolledStudents = course.getEnrolledStudents();
                String assignmentName = assignment.getFileName() != null ? assignment.getFileName() : "an assignment";
                for (Student student : enrolledStudents) {
                    String message = "A new assignment '" + assignmentName + "' has been uploaded for the course: " + course.getCourseName();
                    studentnotificationsService.createStudentNotification(student, message, "assignment_uploaded", course);
                }
            }
        }
    }

    public Assignment getAssignment(Long assessmentId){
      return assignmentRepository.findAssignmentById(assessmentId);
    }

    public List<Assignment> getAssignmentsByCourseId(String courseId){
        return assignmentRepository.findByCourseId(courseId);
    }

    public Assignment getAssignmentByCourseIdAndFileName(String courseId , String fileName){
        List<Assignment> assignmentsInCourse=getAssignmentsByCourseId(courseId);
        for (Assignment assignment : assignmentsInCourse){
            if(Objects.equals(assignment.getFileName(), fileName)){
                return assignment;
            }
        }
        return null;
    }

}
