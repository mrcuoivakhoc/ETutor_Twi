package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.UserDto;
import com.example.Comp1640.Entity.*;
import com.example.Comp1640.Repository.AdminRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

   /* @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String UPLOAD_DIR = "uploads/";


    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Long findIdByUser(String username) {
        return userRepository.findIdByUsername(username);
    }


    @Override
    public List<UserDto> getAllUserDto() {
        List<User> listUsers = userRepository.findAll();
        List<UserDto> listUserDto = new ArrayList<>();

        if (!listUsers.isEmpty()) {
            for(int i = 0; i < listUsers.size(); i++){
                UserDto userDto = new UserDto(listUsers.get(i).getId(),listUsers.get(i).getUsername(),listUsers.get(i).getEmail(),listUsers.get(i).getPassword(),listUsers.get(i).getRole());
                listUserDto.add(userDto);
            }
            return listUserDto;
        } else {
            return null;
        }

    }

    @Override
    public UserDto saveUser(DefinedUser definedUser, MultipartFile file) throws IOException {

        User user = new User(definedUser.getUsername(),definedUser.getEmail(), definedUser.getPassword(), definedUser.getRole());
        UserDto userDto = new UserDto(definedUser.getUsername(),definedUser.getEmail(), definedUser.getPassword(), definedUser.getRole());

       /* user.setPassword(passwordEncoder.encode(userDto.getPassword()));*/
        userRepository.save(user);

        if(Objects.equals(definedUser.getRole().toString(), "STUDENT")){
            Student student = new Student(definedUser.getName(), definedUser.getBirthday(), user, definedUser.getMajor());
            String imageUrl = null;
            if (file != null && !file.isEmpty()) {
                imageUrl = saveFile(file);
            }
            student.setImageFile(imageUrl);
            studentRepository.save(student);
        } else if (Objects.equals(definedUser.getRole().toString(), "TUTOR")) {
            Tutor tutor = new Tutor(definedUser.getName(), definedUser.getBirthday(), user, definedUser.getMajor());
            String imageUrl = null;
            if (file != null && !file.isEmpty()) {
                imageUrl = saveFile(file);
            }
            tutor.setImageFile(imageUrl);
            tutorRepository.save(tutor);
        }else{
            Admin admin = new Admin(definedUser.getName(), definedUser.getBirthday(), definedUser.getImageFile(), user);
            String imageUrl = null;
            if (file != null && !file.isEmpty()) {
                imageUrl = saveFile(file);
            }
            admin.setImageFile(imageUrl);
            adminRepository.save(admin);
        }


        return userDto;

    }


    @Override
    public void deleteUser(Long id) {
        String role = userRepository.findRoleByUserId(id);
        System.out.println(role);

        if(Objects.equals(role, "STUDENT")){
            Student student =  studentRepository.findById(studentRepository.findIdByUserId(id)).get();
            this.deleteFile(student.getImageFile().replace("/uploads/", ""));
        }

        if(Objects.equals(role, "TUTOR")){
            Tutor tutor =  tutorRepository.findById(tutorRepository.findIdByUserId(id)).get();
            this.deleteFile(tutor.getImageFile().replace("/uploads/", ""));
        }

        if(Objects.equals(role, "ADMIN")){
            Admin admin =  adminRepository.findById(adminRepository.findIdByUserId(id)).get();
            this.deleteFile(admin.getImageFile().replace("/uploads/", ""));
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            existingUser.setUsername(userDto.getUsername());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setEmail(userDto.getEmail());
            userRepository.save(existingUser);
            return userDto;

        }
        catch(Exception e){
            return null;
        }
    }


    public boolean deleteFile(String fileName) {
        try {
            Path filePath = Paths.get("uploads", fileName);
            Files.delete(filePath);
            return true; // Xóa thành công
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Xóa thất bại
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Tạo thư mục lưu file nếu chưa có
        Path uploadPath = Path.of(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Tạo tên file duy nhất
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Lưu file vào thư mục
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName; // Trả về đường dẫn file
    }



}
