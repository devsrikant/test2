package com.example.student.controller;

import com.example.student.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Value("${courseURL:http://localhost:8080/api/courses/list}")
    private String courseURL;

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAllCourses() {
        RestTemplate restTemplate = new RestTemplate();
        List<CourseDTO> courseDTO = restTemplate.getForObject("http://localhost:8080/api/courses/list", List.class);
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }
}
