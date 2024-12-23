package com.example.demo.Service.Assessments;

import com.example.demo.Model.Assessments.Assignment;
import com.example.demo.Repository.Assesments.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public void saveAssignment(Assignment assignment){
        assignmentRepository.save(assignment);
    }

    public Assignment getAssignment(Long assessmentId){
      return assignmentRepository.findAssignmentById(assessmentId);
    }

    public List<Assignment> getAssignmentsByCourseId(String courseId){
        return assignmentRepository.findByCourseId(courseId);
    }

    public Assignment getAssignmentByCourseIdAndFileName(String courseId , String fileName){
        List<Assignment> assignmentsInCourse=getAssignmentsByCourseId(courseId);
        for (Assignment assignment : assignmentsInCourse){
            if(Objects.equals(assignment.getFileName(), fileName)){
                return assignment;
            }
        }
        return null;
    }

}
