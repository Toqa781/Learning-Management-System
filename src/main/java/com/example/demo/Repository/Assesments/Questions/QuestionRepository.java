package com.example.demo.Repository.Assesments.Questions;

import com.example.demo.Model.Assessments.Questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByQuestionBankId(Long questionBankId);
}
