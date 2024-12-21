package com.example.demo.Model.Assessments;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.util.Date;

@Setter
@Getter
@Entity

public class Assignment extends Assessment {

    private String fileName;
    @Lob
    @Column(name = "file_content", columnDefinition = "BLOB")
    private byte[] fileContent;

    private String fileMediaType;

    public Assignment(long id, String title, String description, Date deadline, double grade) {
        super(id, title, description, deadline, grade);
    }

    public Assignment(){}

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMediaType() {
        return fileMediaType;
    }

    public void setFileMediaType(String fileMediaType) {
        this.fileMediaType = fileMediaType;
    }
}
