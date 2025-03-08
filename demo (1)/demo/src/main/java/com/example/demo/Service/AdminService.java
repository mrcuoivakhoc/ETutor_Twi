package com.example.demo.Service;

import com.example.demo.Entity.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin saveAdmin(Admin admin, MultipartFile file) throws Exception;
    Optional<Admin> findAdminById(Long id);
    String deleteAdmin(Long id);
    Admin updateAdmin(Long id, Admin admin, MultipartFile file) throws Exception;
}
