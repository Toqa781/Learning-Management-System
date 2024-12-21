package com.example.demo.Repository.Assesments.Submissions;

import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Assessments.Submissions.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission,Long> {

}
