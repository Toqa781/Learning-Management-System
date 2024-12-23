package com.example.demo.Controller.Assessments;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Course;
import com.example.demo.Service.Assessments.AssignmentService;
import com.example.demo.Service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;


@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CourseService courseService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadAssignment(@PathVariable String courseId, @RequestParam("file") MultipartFile file, @RequestParam("assignment") String assignmentJson) {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
        }

        Assignment assignment=assignmentService.getAssignmentByCourseIdAndFileName(courseId , file.getOriginalFilename());
        if (assignment != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("File with same file name is already existed in this Assignemnt.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Assignment newAssignment;
        try {
            objectMapper.registerModule(new JavaTimeModule());
            newAssignment = objectMapper.readValue(assignmentJson, Assignment.class);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid assignment data.");
        }

        try{
            byte[] fileContent = file.getBytes();

            newAssignment.setFileMediaType(file.getContentType());
            newAssignment.setFileName(file.getOriginalFilename());
            newAssignment.setFileContent(fileContent);
            newAssignment.setAssignedDate(LocalDateTime.now());
            newAssignment.setCourseId(courseId);

            assignmentService.saveAssignment(newAssignment);

            return ResponseEntity.status(HttpStatus.CREATED).body(newAssignment);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file: " + e.getMessage());
        }

    }

    @GetMapping("/downloadAssignment/{fileName}")
    public ResponseEntity<?> downloadAssignment(@PathVariable String courseId,@PathVariable String fileName) {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }
        Assignment assignment=assignmentService.getAssignmentByCourseIdAndFileName(courseId , fileName);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment doesn't exist.");
        }

        try{

            byte[] fileContent= assignment.getFileContent();
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            MediaType fileMediaType = MediaType.parseMediaType(assignment.getFileMediaType());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(fileMediaType);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(fileContent.length);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while downloading the file: " + e.getMessage());
        }
    }
}
