package com.example.demo.Service;

import com.example.demo.Entity.Assignment;

import java.util.List;

public interface AssignmentService {
    List<Assignment> getAllAssignments();
    Assignment assignStudent(Long studentId, Long tutorId, Long adminId, String subject);
    Assignment updateAssignment(Long id, Long studentId, Long tutorId, Long adminId, String subject);
    String bulkAssignStudents(List<Long> studentIds, Long tutorId, Long adminId, String subject);
    String deleteAssignment(Long id);
}
