package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudentDto();

    StudentDto getStudentById(Long id);

    String createStudent(StudentDto studentDto, MultipartFile file) throws IOException;

    String updateStudent(Long id, StudentDto studentDto, MultipartFile file) throws IOException;

    String deleteStudent(Long id);
}
