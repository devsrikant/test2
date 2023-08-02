package com.example.student.controller;

import com.example.student.dto.AllocateCourseDTO;
import com.example.student.dto.StudentCourseResponseDTO;
import com.example.student.entity.StudentEntity;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/student/")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("v1/register")
    public ResponseEntity<?> registerStudent(@RequestBody @Valid StudentEntity student) {

        return new ResponseEntity<>(studentService.registerStudent(student), HttpStatus.CREATED);
    }
    @DeleteMapping("v1/delete/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") String studentId) {

        return new ResponseEntity<>(studentService.deleteStudentBYId(studentId), HttpStatus.OK);
    }

    @PostMapping("v1/allocateCoursesToStudent")
    public ResponseEntity<?> registerStudent(@RequestBody @Valid AllocateCourseDTO courseDTO) {
        return new ResponseEntity<>(studentService.allocateCourseToStudent(courseDTO), HttpStatus.CREATED);
    }
    @GetMapping("/allStudentsCourse")
    public ResponseEntity<List<StudentCourseResponseDTO>> findAllStudentsCourses(){

        return new ResponseEntity<>(studentService.getAllStudentDetails(),HttpStatus.OK);

    }
}
