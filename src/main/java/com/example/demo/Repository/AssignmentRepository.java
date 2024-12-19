package com.example.demo.Repository;

import com.example.demo.Model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
}
