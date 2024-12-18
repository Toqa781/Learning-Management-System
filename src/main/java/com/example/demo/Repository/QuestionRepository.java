package com.example.demo.Repository;

import com.example.demo.Model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question,Long> {

}
