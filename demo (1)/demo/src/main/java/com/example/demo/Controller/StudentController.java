package com.example.demo.Controller;

import com.example.demo.Entity.Student;
import com.example.demo.Entity.User;
import com.example.demo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping("/save")
    public ResponseEntity<Student> saveNewStudent(
            @RequestParam("userId") Long userId,
            @ModelAttribute Student student,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        // Gán user ID vào student
        student.setUser(new User());
        student.getUser().setId(userId);

        return ResponseEntity.ok(studentService.saveStudent(student, file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @ModelAttribute Student student,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        return ResponseEntity.ok(studentService.updateStudent(id, student, file));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
