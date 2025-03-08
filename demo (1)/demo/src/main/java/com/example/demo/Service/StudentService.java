package com.example.demo.Service;

import com.example.demo.Entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Student saveStudent(Student student, MultipartFile file) throws Exception;
    Optional<Student> findStudentById(Long id);
    String deleteStudent(Long id);
    Student updateStudent(Long id, Student student, MultipartFile file) throws Exception;
}
