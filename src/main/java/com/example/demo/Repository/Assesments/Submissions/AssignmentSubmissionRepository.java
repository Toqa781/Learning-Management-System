package com.example.demo.Repository.Assesments.Submissions;

import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
}
