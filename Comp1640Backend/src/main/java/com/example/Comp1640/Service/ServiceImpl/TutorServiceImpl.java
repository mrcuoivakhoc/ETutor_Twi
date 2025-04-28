package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.TutorDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.ClassroomRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.MajorService;
import com.example.Comp1640.Service.TutorService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TutorRepository tutorRepository;

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
    public List<TutorDto> getAllTutorDto() {
        List<Tutor> tutors = tutorRepository.findAll();
        List<TutorDto> tutorDtos = new ArrayList<>();
        for (Tutor tutor : tutors) {
            tutorDtos.add(new TutorDto(tutor));
        }
        return tutorDtos;
    }

    @Override
    public TutorDto getTutorById(Long id) {
        Tutor tutor = tutorRepository.findById(id).orElse(null);
        return tutor != null ? new TutorDto(tutor) : null;
    }

    @Override
    public String createTutor(TutorDto tutorDto, MultipartFile file) throws IOException {
        Long userId = userService.findIdByUser(tutorDto.getUsername());
        if (tutorRepository.findIdByUserId(userId) != null) {
            return "User already registered as a tutor!";
        }

        User user = userService.findUserById(userId).orElse(null);
        Major major = majorService.findMajorById(tutorDto.getMajorDto().getId()).orElse(null);
        String imageUrl = null;

        if (file != null && !file.isEmpty()) {
            imageUrl = saveFile(file);
        }

        Tutor tutor = new Tutor();
        tutor.setName(tutorDto.getName());
        tutor.setBirthday(tutorDto.getBirthday());
        tutor.setImageFile(imageUrl);
        tutor.setUser(user);
        tutor.setMajor(major);

        tutorRepository.save(tutor);
        return "Tutor created successfully";
    }

    @Override
    public String updateTutor(Long id, TutorDto tutorDto, MultipartFile file) throws IOException {
        Tutor existingTutor = tutorRepository.findById(id).orElse(null);
        if (existingTutor == null) return "Tutor not found";

        Long userId = userService.findIdByUser(tutorDto.getUsername());
        User user = userService.findUserById(userId).orElse(null);
        Major major = majorService.findMajorById(tutorDto.getMajorDto().getId()).orElse(null);

        if (!Objects.equals(tutorDto.getMajorDto().getId(), existingTutor.getMajor().getId())) {
            classroomRepository.deleteByStudentId(existingTutor.getId()); // nếu mapping theo student thì giữ nguyên
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = saveFile(file);
            deleteFile(existingTutor.getImageFile().replace("/uploads/", ""));
            tutorDto.setImageFile(imageUrl);
        }

        existingTutor.setName(tutorDto.getName());
        existingTutor.setBirthday(tutorDto.getBirthday());
        existingTutor.setImageFile(tutorDto.getImageFile().replace("http://localhost:8080", ""));
        existingTutor.setUser(user);
        existingTutor.setMajor(major);

        tutorRepository.save(existingTutor);
        return "Update successfully";
    }

    @Override
    public String deleteTutor(Long id) {
        Tutor tutor = tutorRepository.findById(id).orElse(null);
        if (tutor != null) {
            deleteFile(tutor.getImageFile().replace("/uploads/", ""));
            classroomRepository.deleteByStudentId(tutor.getId());
            tutorRepository.deleteById(id);
            return "Tutor deleted successfully";
        }
        return "Tutor not found";
    }

    private boolean deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
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
