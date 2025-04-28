package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    @Query(value = "SELECT * FROM schedule WHERE tutor_id = :tutor_id", nativeQuery = true)
    List<Schedule> findByTutorId(@Param("tutor_id") Long tutor_id);


    List<Schedule> findByStudentId(Long studentId);

}
