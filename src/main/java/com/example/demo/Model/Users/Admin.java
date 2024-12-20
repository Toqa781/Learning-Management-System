package com.example.demo.Model.Users;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("ADMIN")

public class Admin extends User {
    //add any specific field
    public Admin() {
        super();

    }
}
