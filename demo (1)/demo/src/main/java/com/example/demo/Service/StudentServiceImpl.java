package com.example.demo.Service;

import com.example.demo.Entity.Student;
import com.example.demo.Entity.User;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student, MultipartFile file) throws IOException {
        if (student.getUser() == null || student.getUser().getId() == null) {
            throw new RuntimeException("User ID is required for Student creation.");
        }

        // Lấy user từ DB
        User user = userRepository.findById(student.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + student.getUser().getId()));

        student.setUser(user);

        // Nếu có file ảnh, lưu vào thư mục uploads/
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            student.setImageFile(fileName);
        }

        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public String deleteStudent(Long id) {
        Student existingStudent = findStudentById(id).orElse(null);
        if (existingStudent == null) {
            return "Student does not exist";
        }
        studentRepository.deleteById(id);
        return "Successfully deleted Student";
    }

    @Override
    public Student updateStudent(Long id, Student student, MultipartFile file) throws Exception {
        Student existingStudent = findStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));

        existingStudent.setRealName(student.getRealName());
        existingStudent.setPhoneNumber(student.getPhoneNumber());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setAddress(student.getAddress());

        // Nếu có file ảnh mới, lưu vào thư mục uploads/
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            existingStudent.setImageFile(fileName);
        }

        return studentRepository.save(existingStudent);
    }
}
