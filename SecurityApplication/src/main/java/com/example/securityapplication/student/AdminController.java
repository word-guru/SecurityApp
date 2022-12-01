package com.example.securityapplication.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/students")
public class AdminController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "John Smith"),
            new Student(2, "James Bond"),
            new Student(3, "Frank Sinatra")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public List <Student> getAllStudents(){
        return  STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") int studentId){
        System.out.println(studentId);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student){
        System.out.println(student);
    }
}
