package com.example.demo.Repository;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Model.Assessments.Questions.QuestionBank;
import com.example.demo.Model.Assessments.Quiz;
import com.example.demo.Model.Courses.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MediaRepository extends JpaRepository<Media,Long> {
    List<Media> findByCourseId(String courseId);
}
