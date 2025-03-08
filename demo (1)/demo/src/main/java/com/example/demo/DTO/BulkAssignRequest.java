package com.example.demo.DTO;

import java.util.List;

public class BulkAssignRequest {
    private List<Long> studentIds;
    private Long tutorId;
    private Long adminId;
    private String subject;

    // Getters và Setters
    public List<Long> getStudentIds() { return studentIds; }
    public void setStudentIds(List<Long> studentIds) { this.studentIds = studentIds; }

    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }

    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
