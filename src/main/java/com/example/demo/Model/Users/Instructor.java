package com.example.demo.Model.Users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("STUDENT")
public class Instructor extends User {
    //add any specific fields
    public Instructor() {
        super();
    }
}
