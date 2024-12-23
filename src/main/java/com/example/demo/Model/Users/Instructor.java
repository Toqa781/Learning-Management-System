package com.example.demo.Model.Users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User {

    public Instructor() {
        super();
    }
}
