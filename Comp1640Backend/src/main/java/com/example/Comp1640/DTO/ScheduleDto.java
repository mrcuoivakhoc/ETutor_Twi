package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class ScheduleDto {

    private Long id;

    private StudentDto studentDto;

    private TutorDto tutorDto;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String status;

    public ScheduleDto() {}

    public ScheduleDto(Long id, StudentDto studentDto, TutorDto tutorDto, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.studentDto = studentDto;
        this.tutorDto = tutorDto;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public ScheduleDto( StudentDto studentDto, TutorDto tutorDto, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.studentDto = studentDto;
        this.tutorDto = tutorDto;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentDto getStudentDto() {
        return studentDto;
    }

    public void setStudentDto(StudentDto studentDto) {
        this.studentDto = studentDto;
    }

    public TutorDto getTutorDto() {
        return tutorDto;
    }

    public void setTutorDto(TutorDto tutorDto) {
        this.tutorDto = tutorDto;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
