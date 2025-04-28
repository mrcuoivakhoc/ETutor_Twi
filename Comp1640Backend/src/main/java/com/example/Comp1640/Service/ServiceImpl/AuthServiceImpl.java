package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.Config.Security.JwtUtils;
import com.example.Comp1640.Entity.LoginRequest;
import com.example.Comp1640.Entity.User;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

   /* @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        /*if(checkPassword(request.getPassword(),user.getPassword())){
            return JwtUtils.generateToken(user.getUsername(), user.getRole().name());
        }else{
            return "Wrong";
//            return JwtUtils.generateToken(user.getUsername(), user.getRole().name());
        }*/
        return JwtUtils.generateToken(user.getUsername(), user.getRole().name());
    }


   /* public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }*/



}
