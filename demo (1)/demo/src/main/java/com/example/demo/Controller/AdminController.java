package com.example.demo.Controller;

import com.example.demo.Entity.Admin;
import com.example.demo.Entity.User;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping()
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PostMapping("/save")
    public ResponseEntity<Admin> saveAdmin(
            @RequestParam("userId") Long userId,
            @ModelAttribute Admin admin,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        // Gán user ID vào admin
        admin.setUser(new User());
        admin.getUser().setId(userId);

        return ResponseEntity.ok(adminService.saveAdmin(admin, file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Long id,
            @ModelAttribute Admin admin,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        return ResponseEntity.ok(adminService.updateAdmin(id, admin, file));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        return adminService.deleteAdmin(id);
    }
}
