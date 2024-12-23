package com.example.demo.Controller.Assessments.Submissions;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import com.example.demo.Model.Course;
import com.example.demo.Service.Assessments.AssignmentService;
import com.example.demo.Service.Assessments.Submissions.AssignmentSubmissionService;
import com.example.demo.Service.Authentication.JWTService;
import com.example.demo.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/assignments")
public class AssignmentSubmissionController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;


    @PostMapping("/{assessmentId}/submit")
    public ResponseEntity<?> submitAssignment(@PathVariable String courseId, @PathVariable Long assessmentId, @RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String tokenHeader) {

        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;
        String studentId;
        try {
            studentId = jwtService.extractId(token, "studentId");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }

        Assignment assignment = assignmentService.getAssignment(assessmentId);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment doesn't exist.");
        }

        if (assignmentSubmissionService.isStudentPrevSubmit(studentId, assessmentId)) {
            return ResponseEntity.status(HttpStatus.FOUND).body("You have already submitted.");
        }

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No file uploaded.");
        }

        if (assignment.getDeadline().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Late Submission");
        }

        try {
            byte[] fileContent = file.getBytes();

            AssignmentSubmission newAssignmentSubmission = new AssignmentSubmission(studentId, assessmentId, LocalDateTime.now(), fileContent);

            assignmentSubmissionService.saveStudentSubmission(newAssignmentSubmission);

            return ResponseEntity.status(HttpStatus.CREATED).body(newAssignmentSubmission);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while Submitting the file: " + e.getMessage());
        }
    }

    @GetMapping("/{assessmentId}/downloadStudentsSubmissions")
    public ResponseEntity<?> downloadStudentsAnswers(@PathVariable String courseId, @PathVariable Long assessmentId) {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }

        Assignment assignment = assignmentService.getAssignment(assessmentId);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment doesn't exist.");
        }

        try {
            List<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionService.getAllStudentsAssignmentSubmissions(assessmentId);

            if (assignmentSubmissions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No submissions found.");
            }

            String directoryPath = "C:/Submissions";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if(!created)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create Folder");
            }

            String zipFilePath = directoryPath + "/Assignment#" + assessmentId.toString() + "_Submissions.zip";

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

                for (AssignmentSubmission assignmentSubmission : assignmentSubmissions) {
                    byte[] fileContent = assignmentSubmission.getFileContent();

                    String fileName = "Student#" + assignmentSubmission.getStudentID();

                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(fileContent);
                    zipOutputStream.closeEntry();
                }
            }

            File zipFile = new File(zipFilePath);

            try (FileOutputStream fos = new FileOutputStream(zipFile)) {
                fos.write(byteArrayOutputStream.toByteArray());
            }

            return ResponseEntity.status(HttpStatus.OK).body("ZIP file saved at: " + zipFilePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while downloading the file: " + e.getMessage());
        }
    }
}
