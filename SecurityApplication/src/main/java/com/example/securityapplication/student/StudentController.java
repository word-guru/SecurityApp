package com.example.securityapplication.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/students")
public class StudentController {


    private static final List<Student> STUDENTS = Arrays.asList(
      new Student(1, "John Smith"),
      new Student(2, "James Bond"),
      new Student(3, "Frank Sinatra")
    );


    @GetMapping("{studentId}")
    public Student GetStudent(@PathVariable("studentId") Integer id){
        return STUDENTS.stream()
                .filter(student -> id.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student not found"));

    }
}
