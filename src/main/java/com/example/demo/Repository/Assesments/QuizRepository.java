package com.example.demo.Repository.Assesments;

import com.example.demo.Model.Assessments.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
