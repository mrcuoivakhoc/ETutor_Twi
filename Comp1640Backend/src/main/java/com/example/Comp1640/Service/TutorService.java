package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.TutorDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TutorService {
    List<TutorDto> getAllTutorDto();
    TutorDto getTutorById(Long id);
    String createTutor(TutorDto tutorDto, MultipartFile file) throws IOException;
    String updateTutor(Long id, TutorDto tutorDto, MultipartFile file) throws IOException;
    String deleteTutor(Long id);
}

