package com.example.demo.Repository;

import com.example.demo.Model.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz,Long> {
}
