package com.example.demo.Service;

import com.example.demo.Entity.Tutor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TutorService {
    List<Tutor> getAllTutors();
    Tutor saveTutor(Tutor tutor, MultipartFile file) throws Exception;
    Optional<Tutor> findTutorById(Long id);
    String deleteTutor(Long id);
    Tutor updateTutor(Long id, Tutor tutor, MultipartFile file) throws Exception;
}
