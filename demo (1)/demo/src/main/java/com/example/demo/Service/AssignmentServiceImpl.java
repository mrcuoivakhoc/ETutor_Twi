package com.example.demo.Service;

import com.example.demo.Entity.Assignment;
import com.example.demo.Entity.Student;
import com.example.demo.Entity.Tutor;
import com.example.demo.Entity.Admin;
import com.example.demo.Repository.AssignmentRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.TutorRepository;
import com.example.demo.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private NotificationService notificationService;

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment assignStudent(Long studentId, Long tutorId, Long adminId, String subject) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student ID " + studentId + " not found!"));
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor ID " + tutorId + " not found!"));
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin ID " + adminId + " not found!"));

        Assignment assignment = new Assignment();
        assignment.setStudent(student);
        assignment.setTutor(tutor);
        assignment.setAdmin(admin);
        assignment.setSubject(subject);
        // 🔔 Send Notification
        notificationService.sendNotification(student.getUser(), "You have been assigned to a new subject: " + subject);
        notificationService.sendNotification(tutor.getUser(), "You have been assigned multiple new students for the subject: " + subject);
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment updateAssignment(Long id, Long studentId, Long tutorId, Long adminId, String subject) {
        Assignment existingAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment ID " + id + " not found!"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student ID " + studentId + " not found!"));
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor ID " + tutorId + " not found!"));
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin ID " + adminId + " not found!"));

        existingAssignment.setStudent(student);
        existingAssignment.setTutor(tutor);
        existingAssignment.setAdmin(admin);
        existingAssignment.setSubject(subject);

        return assignmentRepository.save(existingAssignment);
    }

    @Override
    public String bulkAssignStudents(List<Long> studentIds, Long tutorId, Long adminId, String subject) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor ID " + tutorId + " not found!"));
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin ID " + adminId + " not found!"));

        for (Long studentId : studentIds) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student ID " + studentId + " not found!"));

            Assignment assignment = new Assignment();
            assignment.setStudent(student);
            assignment.setTutor(tutor);
            assignment.setAdmin(admin);
            assignment.setSubject(subject);
            assignmentRepository.save(assignment);
            notificationService.sendNotification(student.getUser(), "You have been assigned to a new subject: " + subject);
        }
        notificationService.sendNotification(tutor.getUser(), "You have been assigned multiple new students for the subject: " + subject);
        return "Bulk assignment completed successfully!";
    }

    @Override
    public String deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            return "Assignment does not exist";
        }
        assignmentRepository.deleteById(id);
        return "Successfully deleted Assignment";
    }
}
