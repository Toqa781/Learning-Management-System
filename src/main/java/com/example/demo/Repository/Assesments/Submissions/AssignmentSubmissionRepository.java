package com.example.demo.Repository.Assesments.Submissions;

import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
    List<AssignmentSubmission> findAssignmentSubmissionByAssessmentId(long assessmentId);
}
