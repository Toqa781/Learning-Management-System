package com.example.demo.Repository.Assesments.Submissions;

import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import com.example.demo.Model.Assessments.Submissions.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission,Long> {
    List<QuizSubmission> findQuizSubmissionByAssessmentId(long assessmentId);

}
