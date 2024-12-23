package com.example.demo.Controller.Assessments;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import com.example.demo.Model.Assessments.Submissions.QuizSubmission;
import com.example.demo.Model.Course;
import com.example.demo.Service.Assessments.AssignmentService;
import com.example.demo.Service.Assessments.ExcelGenerateService;
import com.example.demo.Service.Assessments.QuizService;
import com.example.demo.Service.Assessments.Submissions.AssignmentSubmissionService;
import com.example.demo.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}")
public class ExcelGenerateController {

    @Autowired
    private ExcelGenerateService excelGenerateService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;
    @Autowired
    private QuizService quizService;

    @GetMapping("/assignments/generateExcel")
    public ResponseEntity<?> generateAssignmentExcelReport(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseId(courseId);

        List<List<AssignmentSubmission>> assignmentsSubmissions = new ArrayList<>();

        for (Assignment assignment : assignments) {
            List<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionService.getAllStudentsAssignmentSubmissions(assignment.getId());
            assignmentsSubmissions.add(assignmentSubmissions);
        }

        try {
            byte[] fileContent = excelGenerateService.generateExcelForAssignments(assignmentsSubmissions, courseId);

            if (fileContent == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to generate Assignments Excel file");
            }

            return downloadingExcel(fileContent, courseId, "Assignment");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate Assignments Excel file: " + e.getMessage());
        }
    }

    @GetMapping("/quizes/generateExcel")
    public ResponseEntity<?> generateQuizExcelReport(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }
        List<Quiz> quizes = quizService.getQuizesInCourse(courseId);

        List<List<QuizSubmission>> quizesSubmissions = new ArrayList<>();

        for (Quiz quiz : quizes) {
            List<QuizSubmission> quizSubmissions = quizService.getAllStudentsQuizSubmissions(quiz.getId());
            quizesSubmissions.add(quizSubmissions);
        }

        try {
            byte[] fileContent = excelGenerateService.generateExcelForQuizes(quizesSubmissions, courseId);

            if (fileContent == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to generate Quiz Excel file");
            }

            return downloadingExcel(fileContent, courseId, "Quiz");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate Quiz Excel file: " + e.getMessage());
        }
    }

    private ResponseEntity<?> downloadingExcel(byte[] excelBytes, String courseId, String title) {
        String directoryPath = "C:/Reports";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create folder to save the Excel file.");
            }
        }

        String filePath = directoryPath + "/Course#" + courseId + "_" + title + ".xlsx";

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(excelBytes);
            return ResponseEntity.status(HttpStatus.OK).body("Excel file saved at: " + filePath);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while downloading the file: " + e.getMessage());
        }
    }
}
