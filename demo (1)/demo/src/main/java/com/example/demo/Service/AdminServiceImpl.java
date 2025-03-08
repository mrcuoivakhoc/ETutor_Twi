package com.example.demo.Service;

import com.example.demo.Entity.Admin;
import com.example.demo.Entity.User;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin saveAdmin(Admin admin, MultipartFile file) throws IOException {
        if (admin.getUser() == null || admin.getUser().getId() == null) {
            throw new RuntimeException("User ID is required for Admin creation.");
        }

        // Lấy user từ DB
        User user = userRepository.findById(admin.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + admin.getUser().getId()));

        admin.setUser(user);

        // Nếu có file ảnh, lưu vào thư mục uploads/
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            admin.setImageFile(fileName);
        }

        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> findAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public String deleteAdmin(Long id) {
        Admin existingAdmin = findAdminById(id).orElse(null);
        if (existingAdmin == null) {
            return "Admin does not exist";
        }
        adminRepository.deleteById(id);
        return "Successfully deleted Admin";
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin, MultipartFile file) throws Exception {
        Admin existingAdmin = findAdminById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));

        existingAdmin.setRealName(admin.getRealName());
        existingAdmin.setPhoneNumber(admin.getPhoneNumber());
        existingAdmin.setEmail(admin.getEmail());

        // Nếu có file ảnh mới, lưu vào thư mục uploads/
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            existingAdmin.setImageFile(fileName);
        }

        return adminRepository.save(existingAdmin);
    }
}
