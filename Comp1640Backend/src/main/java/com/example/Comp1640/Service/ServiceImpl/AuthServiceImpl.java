package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.Config.Security.JwtUtils;
import com.example.Comp1640.Entity.LoginRequest;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println(JwtUtils.generateToken(user.getUsername(), user.getRole().name()));
        return JwtUtils.generateToken(user.getUsername(), user.getRole().name());
    }



}
