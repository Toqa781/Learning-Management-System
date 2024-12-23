package com.example.demo.Service.Assessments.Submissions;

import com.example.demo.Model.Assessments.Submissions.AssignmentSubmission;
import com.example.demo.Repository.Assesments.Submissions.AssignmentSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Service
public class AssignmentSubmissionService {
    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    public void saveStudentSubmission(AssignmentSubmission assignmentSubmission) {
        assignmentSubmissionRepository.save(assignmentSubmission);
    }

   public List<AssignmentSubmission> getAllStudentsAssignmentSubmissions(long assessmentId){
        return assignmentSubmissionRepository.findAssignmentSubmissionByAssessmentId(assessmentId);
   }

    public boolean isStudentPrevSubmit(String studentId ,Long assessmentId){
        AssignmentSubmission assignmentSubmission = getStudentAssignmentSubmission(studentId,assessmentId);
        if(assignmentSubmission != null){ return true;}
        return false;
    }

    public AssignmentSubmission getStudentAssignmentSubmission(String studentId ,Long assessmentId){
        List<AssignmentSubmission> studentSubmissions=getAllStudentsAssignmentSubmissions(assessmentId);
        for(AssignmentSubmission assignmentSubmission :studentSubmissions){
            if(Objects.equals(studentId, assignmentSubmission.getStudentID())){
                return assignmentSubmission;
            }
        }
        return null;
    }
}
