package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT id FROM user WHERE username = :username", nativeQuery = true)
    Long findIdByUsername(@Param("username") String username);

    @Query(value = "SELECT role FROM user WHERE id = :userId", nativeQuery = true)
    String findRoleByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM student WHERE user_id = :userId", nativeQuery = true)
    void deleteStudentByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tutor WHERE user_id = :userId", nativeQuery = true)
    void deleteTutorByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM admin WHERE user_id = :userId", nativeQuery = true)
    void deleteAdminByUserId(@Param("userId") Long userId);

    Optional<User> findByUsername(String username);

}
