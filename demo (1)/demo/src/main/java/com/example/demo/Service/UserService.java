package com.example.demo.Service;

import com.example.demo.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user) throws Exception;
    Optional<User> findUserById(Long id);
    String deleteUser(Long id);
    User updateUser(Long id, User user) throws Exception;

}
