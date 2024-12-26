package com.example.demo.Model.Courses;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Media {
    @Id
    private Long id;

    private String title;

    private String description;

    private String fileName;

    @Lob
    @Column(name = "file_content", columnDefinition = "BLOB")
    private byte[] fileContent;

    private String fileMediaType;

    private LocalDateTime uploadDate;

    private String courseId;


    public Media(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Media() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileMediaType() {
        return fileMediaType;
    }

    public void setFileMediaType(String fileMediaType) {
        this.fileMediaType = fileMediaType;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
