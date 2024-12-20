package com.example.demo.Repository.Assesments.Questions;

import com.example.demo.Model.Assessments.Questions.Question;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank,Long> {
   QuestionBank findByCourseId(String courseId);
}
