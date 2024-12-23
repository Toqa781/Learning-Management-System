package com.example.demo.Model.Users;

import com.example.demo.Model.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User {
    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<Course> courses;

    public Instructor() {
        super();
    }
}
