package com.example.demo.Controller;

import com.example.demo.Model.Course;
import com.example.demo.Model.Courses.Media;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.MediaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;


@RestController
@CrossOrigin
@RequestMapping("/courses/{courseId}/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedia(@PathVariable String courseId, @RequestParam("file") MultipartFile file, @RequestParam("media") String mediaJson) {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course doesn't exist.");
        }
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
        }

        Media media = mediaService.getMediaByCourseIdAndFileName(courseId, file.getOriginalFilename());
        if (media != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Media with the same file name is already exists in this course");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Media newMedia;
        try {
            objectMapper.registerModule(new JavaTimeModule());
            newMedia = objectMapper.readValue(mediaJson, Media.class);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid media data.");
        }

        try {
            byte[] fileContent = file.getBytes();

            newMedia.setFileMediaType(file.getContentType());
            newMedia.setFileName(file.getOriginalFilename());
            newMedia.setFileContent(fileContent);
            newMedia.setUploadDate(LocalDateTime.now());
            newMedia.setCourseId(courseId);

            mediaService.saveMedia(newMedia);

            return ResponseEntity.status(HttpStatus.CREATED).body(newMedia);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file: " + e.getMessage());
        }

    }

    @GetMapping("/downloadMedia/{fileName}")
    public ResponseEntity<?> downloadMedia(@PathVariable String courseId, @PathVariable String fileName) {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course doesn't exist.");
        }
        Media media = mediaService.getMediaByCourseIdAndFileName(courseId, fileName);
        if (media == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media doesn't exist.");
        }

        try {

            byte[] fileContent = media.getFileContent();
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            MediaType fileMediaType = MediaType.parseMediaType(media.getFileMediaType());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(fileMediaType);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(fileContent.length);

            return downloadFile(media);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while downloading the file: " + e.getMessage());
        }
    }

    private ResponseEntity<?> downloadFile(Media media) {
        String directoryPath = "C:/SW_Project/Media";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create folder to save the Media file.");
            }
        }

        String filePath = directoryPath + "/Course#" + media.getCourseId() + "_" + media.getFileName();

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(media.getFileContent());
            return ResponseEntity.status(HttpStatus.OK).body("Media file saved at: " + filePath);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while downloading the file: " + e.getMessage());
        }
    }


}
