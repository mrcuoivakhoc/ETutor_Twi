package com.example.demo.Controller;

import com.example.demo.DTO.BulkAssignRequest;
import com.example.demo.Entity.Assignment;
import com.example.demo.Service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }

    @PostMapping("/assign")
    public ResponseEntity<Assignment> assignStudent(
            @RequestParam("studentId") Long studentId,
            @RequestParam("tutorId") Long tutorId,
            @RequestParam("adminId") Long adminId,
            @RequestParam("subject") String subject) {
        return ResponseEntity.ok(assignmentService.assignStudent(studentId, tutorId, adminId, subject));
    }
    @PostMapping("/bulk-assign")
    public ResponseEntity<String> bulkAssignStudents(@RequestBody BulkAssignRequest request) {
        return ResponseEntity.ok(assignmentService.bulkAssignStudents(
                request.getStudentIds(), request.getTutorId(), request.getAdminId(), request.getSubject()
        ));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable Long id,
            @RequestParam("studentId") Long studentId,
            @RequestParam("tutorId") Long tutorId,
            @RequestParam("adminId") Long adminId,
            @RequestParam("subject") String subject) {
        return ResponseEntity.ok(assignmentService.updateAssignment(id, studentId, tutorId, adminId, subject));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.deleteAssignment(id));
    }
}
