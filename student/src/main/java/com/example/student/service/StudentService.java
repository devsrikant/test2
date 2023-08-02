package com.example.student.service;

import com.example.student.dto.AllocateCourseDTO;
import com.example.student.dto.StudentCourseResponseDTO;
import com.example.student.entity.StudentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

     StudentEntity registerStudent(StudentEntity student);
     List<StudentCourseResponseDTO> getAllStudentDetails();
     String deleteStudentBYId(String id);
     AllocateCourseDTO allocateCourseToStudent(AllocateCourseDTO allocateCourseDTO) ;
}
