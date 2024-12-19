package com.example.demo.Repository.Assesments;

import com.example.demo.Model.Assessments.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
}
