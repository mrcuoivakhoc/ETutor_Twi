package com.example.demo.Controller;

import com.example.demo.Entity.Tutor;
import com.example.demo.Entity.User;
import com.example.demo.Service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping()
    public ResponseEntity<List<Tutor>> getAllTutors() {
        return ResponseEntity.ok(tutorService.getAllTutors());
    }

    @PostMapping("/save")
    public ResponseEntity<Tutor> saveTutor(
            @RequestParam("userId") Long userId,
            @ModelAttribute Tutor tutor,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        // Gán user ID vào tutor
        tutor.setUser(new User());
        tutor.getUser().setId(userId);

        return ResponseEntity.ok(tutorService.saveTutor(tutor, file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tutor> updateTutor(
            @PathVariable Long id,
            @ModelAttribute Tutor tutor,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        return ResponseEntity.ok(tutorService.updateTutor(id, tutor, file));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTutor(@PathVariable Long id) {
        return tutorService.deleteTutor(id);
    }
}
