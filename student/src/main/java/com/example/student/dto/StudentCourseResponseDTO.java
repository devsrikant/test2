package com.example.student.dto;

import com.example.student.entity.StudentEntity;

import java.util.List;
import java.util.Set;

public class StudentCourseResponseDTO {

    private StudentEntity student;
    private List<String> courseNames;

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(List<String> courseNames) {
        this.courseNames = courseNames;
    }
}
