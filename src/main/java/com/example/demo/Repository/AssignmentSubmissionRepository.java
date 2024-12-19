package com.example.demo.Repository;

import com.example.demo.Model.Assessment;
import com.example.demo.Model.Assignment;
import com.example.demo.Model.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
}
