package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query(value = "SELECT id FROM tutor WHERE user_id = :id", nativeQuery = true)
    Long findIdByUserId(@Param("id") Long id);


    @Query(value = "SELECT id FROM tutor WHERE id_major = :id", nativeQuery = true)
    List<Long> findIdTutorsByMajorId(@Param("id") Long id);

}
