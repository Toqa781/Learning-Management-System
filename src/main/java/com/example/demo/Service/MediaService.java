package com.example.demo.Service;

import com.example.demo.Model.Course;
import com.example.demo.Model.Courses.Media;
import com.example.demo.Model.Users.Student;
import com.example.demo.Repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private StudentNotificationsService studentnotificationsService;

    public void saveMedia(Media media){
        mediaRepository.save(media);
        // Notify students about the new Media
        if (media.getCourseId() != null) {
            Course course = courseService.getCourseById(media.getCourseId());
            if (course != null) {
                List<Student> enrolledStudents = course.getEnrolledStudents();
                String assignmentName = media.getFileName() != null ? media.getFileName() : "a Media";
                for (Student student : enrolledStudents) {
                    String message = "A new Media '" + assignmentName + "' has been uploaded for the course: " + course.getCourseName();
                    studentnotificationsService.createStudentNotification(student, message, "Media_uploaded", course);
                }
            }
        }
    }

//    public Media getMedia(Long mediaId){
//        return mediaRepository.findById(mediaId).orElse(null);
//    }

    public List<Media> getMediaByCourseId(String courseId){
        return mediaRepository.findByCourseId(courseId);
    }

    public Media getMediaByCourseIdAndFileName(String courseId , String fileName){
        List<Media> mediaInCourse=getMediaByCourseId(courseId);
        for (Media media : mediaInCourse){
            if(Objects.equals(media.getFileName(), fileName)){
                return media;
            }
        }
        return null;
    }

    public List<Media> getAssignmentsByCourseId(String courseId){
        return mediaRepository.findByCourseId(courseId);
    }

}
