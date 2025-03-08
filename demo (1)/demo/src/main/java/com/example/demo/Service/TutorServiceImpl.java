package com.example.demo.Service;

import com.example.demo.Entity.Tutor;
import com.example.demo.Entity.User;
import com.example.demo.Repository.TutorRepository;
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
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    @Override
    public Tutor saveTutor(Tutor tutor, MultipartFile file) throws IOException {
        if (tutor.getUser() == null || tutor.getUser().getId() == null) {
            throw new RuntimeException("User ID is required for Tutor creation.");
        }

        // Lấy user từ DB
        User user = userRepository.findById(tutor.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + tutor.getUser().getId()));

        tutor.setUser(user);

        // Nếu có file ảnh, lưu vào thư mục uploads/
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            tutor.setImageFile(fileName);
        }

        return tutorRepository.save(tutor);
    }

    @Override
    public Optional<Tutor> findTutorById(Long id) {
        return tutorRepository.findById(id);
    }

    @Override
    public String deleteTutor(Long id) {
        Tutor existingTutor = findTutorById(id).orElse(null);
        if (existingTutor == null) {
            return "Tutor does not exist";
        }
        tutorRepository.deleteById(id);
        return "Successfully deleted Tutor";
    }

    @Override
    public Tutor updateTutor(Long id, Tutor tutor, MultipartFile file) throws Exception {
        Tutor existingTutor = findTutorById(id)
                .orElseThrow(() -> new RuntimeException("Tutor not found with ID: " + id));

        existingTutor.setRealName(tutor.getRealName());
        existingTutor.setPhoneNumber(tutor.getPhoneNumber());
        existingTutor.setDateOfBirth(tutor.getDateOfBirth());
        existingTutor.setEmail(tutor.getEmail());
        existingTutor.setProfessionalQualification(tutor.getProfessionalQualification());

        // Nếu có file ảnh mới, lưu vào thư mục uploads/
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            existingTutor.setImageFile(fileName);
        }

        return tutorRepository.save(existingTutor);
    }
}
