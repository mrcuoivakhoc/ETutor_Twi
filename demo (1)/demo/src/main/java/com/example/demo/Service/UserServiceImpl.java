package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) throws Exception {
        if (user == null) {
            throw new Exception("User cannot be null");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public String deleteUser(Long id) {
        User existingUser = findUserById(id).orElse(null);
        if (existingUser == null) {
            return "User does not exist";
        } else {
            userRepository.deleteById(id);
            return "Successfully deleted User";
        }
    }

    @Override
    public User updateUser(Long id, User user) throws Exception {
        User existingUser = findUserById(id).orElse(null);
        if (existingUser == null) {
            throw new Exception("User not found");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }
}
