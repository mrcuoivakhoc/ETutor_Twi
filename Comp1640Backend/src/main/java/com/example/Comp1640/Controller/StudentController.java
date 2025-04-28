package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.Service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ✅ Get all students
    @GetMapping()
    public ResponseEntity<List<StudentDto>> getAllStudentDto(HttpServletRequest request) {
        List<StudentDto> studentDtos = studentService.getAllStudentDto();
        if (studentDtos != null && !studentDtos.isEmpty()) {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            for (StudentDto studentDto : studentDtos) {
                studentDto.setImageFile(baseUrl + studentDto.getImageFile());
            }
            return ResponseEntity.ok(studentDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // ✅ Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id, HttpServletRequest request) {
        StudentDto studentDto = studentService.getStudentById(id);
        if (studentDto != null) {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            studentDto.setImageFile(baseUrl + studentDto.getImageFile());
            return ResponseEntity.ok(studentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Create student
    @PostMapping("/create")
    public ResponseEntity<String> create(@ModelAttribute StudentDto studentDto,
                                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(studentService.createStudent(studentDto, file));
    }

    // ✅ Update student
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @ModelAttribute StudentDto studentDto,
                                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto, file));
    }

    // ✅ Delete student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }
}
