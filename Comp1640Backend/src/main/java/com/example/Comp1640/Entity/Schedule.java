package com.example.Comp1640.Entity;


import com.example.Comp1640.DTO.ScheduleDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutorId", nullable = false)
    private Tutor tutor;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status;

    public Schedule() {}

    public Schedule(Long id, Student student, Tutor tutor, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = id;
        this.student = student;
        this.tutor = tutor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Schedule( Student student, Tutor tutor, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.student = student;
        this.tutor = tutor;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
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
