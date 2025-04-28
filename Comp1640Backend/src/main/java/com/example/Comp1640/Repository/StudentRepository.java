package com.example.Comp1640.Repository;

import com.example.Comp1640.DTO.Dashboard.MajorCountDto;
import com.example.Comp1640.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT id FROM student WHERE user_id = :id", nativeQuery = true)
    Long findIdByUserId(@Param("id") Long id);

    @Query(value = "SELECT id FROM student WHERE id_major = :id", nativeQuery = true)
    List<Long> findIdStudentsByMajorId(@Param("id") Long id);

    @Query("SELECT new com.example.Comp1640.DTO.Dashboard.MajorCountDto(m.name, COUNT(s)) FROM Student s JOIN s.major m GROUP BY m.name")
    List<MajorCountDto> countStudentsByMajor();

}
