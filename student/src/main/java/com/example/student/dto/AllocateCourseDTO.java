package com.example.student.dto;

import java.util.List;
import java.util.Set;

public class AllocateCourseDTO {

    private String studentId;
    private List<String> courseIds;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<String> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<String> courseIds) {
        this.courseIds = courseIds;
    }
}
