package com.example.student.service.serviceimpl;

import com.example.student.dto.AllocateCourseDTO;
import com.example.student.dto.CourseDTO;
import com.example.student.dto.StudentCourseResponseDTO;
import com.example.student.entity.StudentEntity;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.repository.StudentRepo;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;
    @Value ( "${courseURL:http://localhost:8080/api/courses}" )
    private String courseURL;

    @Override
    public StudentEntity registerStudent(StudentEntity student) {
        return studentRepo.save (student);
    }

    @Override
    public List<StudentCourseResponseDTO> getAllStudentDetails() {
        List<StudentEntity> studentEntityList = studentRepo.findAll ();
        List<StudentCourseResponseDTO> studentCourseResponseDTOS = new ArrayList<> ();
        //get the set of courseids
        // Set<String> courseIds = convertStringToSet(studentEntityList.stream().map(StudentEntity::getCourseIds).collect(Collectors.joining(",")));
        //call the course service for getting the name of the course Map<id,name> as response
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<CourseDTO>> courseResponse = restTemplate.exchange(
                "http://localhost:8080/api/courses/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CourseDTO>> () {}
        );
        List<CourseDTO> courseDTO = courseResponse.getBody();   studentEntityList.forEach (s -> {
            StudentCourseResponseDTO dto = new StudentCourseResponseDTO ();
            StudentEntity student = new StudentEntity ();
            student.setId (s.getId ());
            student.setFullName (s.getFullName ());
            student.setEmail (s.getEmail ());
            student.setMobileNum (s.getMobileNum ());
            dto.setStudent (student);
            if (null != s.getCourseIds () && !s.getCourseIds ().isEmpty ()) {
                List<String> set =  new ArrayList<> ( convertStringToSet(s.getCourseIds())) ;
                List<String> courseName = new ArrayList<> ();

                for (String n : set) {
                    for (CourseDTO course : courseDTO) {
                        if (course.getId().toString ().equals (n)) {
                            courseName.add(course.getName());
                            break; // Found the matching course, break the inner loop
                        }
                    }
                }

                dto.setCourseNames (courseName);
                studentCourseResponseDTOS.add (dto);
            }
        });
        return studentCourseResponseDTOS;
    }

    @Override
    public String deleteStudentBYId(String id) {
        studentRepo.deleteById (id);
        return "deleted successfully";
    }

    @Override
    public AllocateCourseDTO allocateCourseToStudent(AllocateCourseDTO allocateCourseDTO) {
        //we have to check student id is exists or not in student db
        Optional<StudentEntity> student = Optional.ofNullable (studentRepo.findById (allocateCourseDTO.getStudentId ())
                .orElseThrow (() -> new StudentNotFoundException ("No student available ID: " + allocateCourseDTO.getStudentId ())));
        List<String> existsCourse = new ArrayList<> ();
        if (null != student.get ().getCourseIds () && !student.get ().getCourseIds ().isEmpty ()) {
            existsCourse = convertStringToSet (student.get ().getCourseIds ());
        } else {
            existsCourse.addAll (allocateCourseDTO.getCourseIds ());
        }

        student.get ().setCourseIds (convertToString (existsCourse));
        StudentEntity studentSaved = studentRepo.save (student.get ());
        AllocateCourseDTO allocateCourseDTORes = new AllocateCourseDTO ();
        allocateCourseDTORes.setCourseIds (convertStringToSet (studentSaved.getCourseIds ()));
        allocateCourseDTORes.setStudentId (studentSaved.getId ());
        return allocateCourseDTORes;
    }

    private String convertToString(List<String> courseIds) {
        return courseIds.stream ().map (Object::toString).collect (Collectors.joining (","));
    }

    private List<String> convertStringToSet(String courseIds) {
        return Stream.of (courseIds.split (","))
                .map (String::trim)
                .collect (Collectors.toList ());
    }
}
