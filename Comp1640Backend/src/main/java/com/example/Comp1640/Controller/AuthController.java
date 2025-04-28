package com.example.Comp1640.Controller;


import com.example.Comp1640.Entity.LoginRequest;
import com.example.Comp1640.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);  // Gọi hàm login từ service
        System.out.println("Token: " + token);
        return ResponseEntity.ok(authService.login(request));
    }
}
