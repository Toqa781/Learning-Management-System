package com.example.demo.Repository.Assesments;

import com.example.demo.Model.Assessments.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findByCourseId(String courseId);

    Assignment findAssignmentById(Long assessmentId);

}
