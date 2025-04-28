package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Service.MajorService;
import com.example.Comp1640.Service.StudentService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MajorService majorService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public List<StudentDto> getAllStudentDto() {
        List<Student> listStudents = studentRepository.findAll();
        List<StudentDto> listStudentDto = new ArrayList<>();

        for (Student student : listStudents) {
            listStudentDto.add(new StudentDto(student));
        }

        return listStudentDto;
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student != null ? new StudentDto(student) : null;
    }

    @Override
    public String createStudent(StudentDto studentDto, MultipartFile file) throws IOException {
        Long userId = userService.findIdByUser(studentDto.getUsername());
        User user = userService.findUserById(userId).orElse(null);
        Major major = majorService.findMajorById(studentDto.getMajorDto().getId()).orElse(null);

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = saveFile(file);
        }

        Student student = new Student();
        student.setName(studentDto.getName());
        student.setBirthday(studentDto.getBirthday());
        student.setImageFile(imageUrl);
        student.setUser(user);
        student.setMajor(major);

        studentRepository.save(student);
        return "Student created successfully";
    }

    @Override
    public String updateStudent(Long id, StudentDto studentDto, MultipartFile file) throws IOException {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent == null) return "Student not found";

        Long userId = userService.findIdByUser(studentDto.getUsername());
        User user = userService.findUserById(userId).orElse(null);
        Major major = majorService.findMajorById(studentDto.getMajorDto().getId()).orElse(null);

        if (!Objects.equals(studentDto.getMajorDto().getId(), existingStudent.getMajor().getId())) {
            classroomRepository.deleteByStudentId(existingStudent.getId());
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = saveFile(file);
            this.deleteFile(existingStudent.getImageFile().replace("/uploads/", ""));
            studentDto.setImageFile(imageUrl);
        }

        existingStudent.setName(studentDto.getName());
        existingStudent.setBirthday(studentDto.getBirthday());
        existingStudent.setImageFile(studentDto.getImageFile().replace("http://localhost:8080", ""));
        existingStudent.setUser(user);
        existingStudent.setMajor(major);
        studentRepository.save(existingStudent);

        return "Update successfully";
    }

    @Override
    public String deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            deleteFile(student.getImageFile().replace("/uploads/", ""));
            classroomRepository.deleteByStudentId(student.getId());
            studentRepository.deleteById(id);
            return "Student deleted successfully";
        }
        return "Student not found";
    }

    private boolean deleteFile(String fileName) {
        try {
            Path filePath = Paths.get("uploads", fileName);
            Files.delete(filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Path.of(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName;
    }
}
