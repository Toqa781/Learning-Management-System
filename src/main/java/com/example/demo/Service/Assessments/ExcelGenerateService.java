package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import com.example.demo.Model.Assessments.Submissions.QuizSubmission;
import org.apache.coyote.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.Model.Assessments.Submissions.Submission;
import com.example.demo.Service.Assessments.Submissions.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelGenerateService {
    //cs222
    //a1
    //a2

    public byte[] generateExcelForAssignments(List<List<AssignmentSubmission>> studentsSubmissions, String courseId) {
        try (Workbook workbook = new XSSFWorkbook()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String sheetTitle = "AssignmentTracking";

            Sheet sheet = workbook.createSheet(sheetTitle);

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Student ID", "Assignment ID", "Submission Date", "Grade"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }

            int rowIdx = 1;
            for (List<AssignmentSubmission> assignmentSubmissions : studentsSubmissions) {
                for (AssignmentSubmission studentSubmission : assignmentSubmissions) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(studentSubmission.getStudentID());
                    row.createCell(1).setCellValue(studentSubmission.getAssessmentId());
                    row.createCell(2).setCellValue(studentSubmission.getSubmissionDate().toString());
                    row.createCell(3).setCellValue(studentSubmission.getGrade());
                }

            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }


            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            System.out.println("Error While Generating File");
        }
        return null;
    }

    public byte[] generateExcelForQuizes(List<List<QuizSubmission>> studentsSubmissions, String courseId) {
        try (Workbook workbook = new XSSFWorkbook()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String sheetTitle = "QuizTracking";

            Sheet sheet = workbook.createSheet(sheetTitle);

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Student ID", "Quiz ID", "Submission Date", "Grade" };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }


            int rowIdx = 1;
            for (List<QuizSubmission> quizSubmissions : studentsSubmissions) {
                for (QuizSubmission studentSubmission : quizSubmissions) {
                    Row row = sheet.createRow(rowIdx++);

                    row.createCell(0).setCellValue(studentSubmission.getStudentID());
                    row.createCell(1).setCellValue(studentSubmission.getAssessmentId());
                    row.createCell(2).setCellValue(studentSubmission.getSubmissionDate().toString());
                    row.createCell(3).setCellValue(studentSubmission.getGrade());
                    System.out.println("error in items end?");
                }

            }
            System.out.println("here6");


            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            System.out.println("here7");

            workbook.write(outputStream);
            System.out.println("here8");

            return outputStream.toByteArray();

        } catch (Exception e) {
            System.out.println("Error While Generating File");
        }
        return null;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

//    private CellStyle createRedStyle(Workbook workbook) {
//        CellStyle style =workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setColor(IndexedColors.RED.getIndex());
//        style.setFont(font);
//        return style;
//    }
//    private CellStyle createGreenStyle(Workbook workbook) {
//        CellStyle style =workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setColor(IndexedColors.GREEN.getIndex());
//        style.setFont(font);
//        return style;
//    }
//
}
