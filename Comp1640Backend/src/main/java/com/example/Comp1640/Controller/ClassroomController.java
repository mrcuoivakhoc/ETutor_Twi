package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;


    @GetMapping()
    public ResponseEntity<List<ClassroomDto>> getAllClassroomDto() {
        return ResponseEntity.ok(classroomService.getAllClassroomDto());
    }

    @PostMapping("/save_classroom")
    public ResponseEntity<String> saveNew(@RequestBody(required = false) ClassroomDto classroomDto) {
        return ResponseEntity.ok(classroomService.saveClassroom(classroomDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody(required = false) ClassroomDto classroomDto ) {
        return ResponseEntity.ok(classroomService.updateClassroom(classroomDto));
    }

    @DeleteMapping("/delete/{tutorId}")
    public void deleteClassroom(@PathVariable Long tutorId) {
        classroomService.deleteClassroom(tutorId);
    }

}
