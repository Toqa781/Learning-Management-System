package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Repository.Assesments.AssignmentRepository;

public class AssignmentService {
    private AssignmentRepository assignmentRepository;

    AssignmentService(AssignmentRepository assignmentRepository) {}

    public void saveAssignment(Assignment assignment){
        assignmentRepository.save(assignment);
    }
}
