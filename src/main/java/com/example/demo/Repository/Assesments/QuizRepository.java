package com.example.demo.Repository.Assesments;

import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Assessments.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByCourseId(String courseId);
}
