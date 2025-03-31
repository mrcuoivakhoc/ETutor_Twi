package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTutorId(Long tutorId);
    List<Schedule> findByStudentId(Long studentId);

}
