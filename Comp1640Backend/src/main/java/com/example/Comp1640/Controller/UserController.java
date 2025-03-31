package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.UserDto;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUserDto() {
        return ResponseEntity.ok(userService.getAllUserDto());
    }

    @PostMapping("/save_user")
    public ResponseEntity<UserDto> saveNew(@ModelAttribute DefinedUser definedUser, @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.saveUser(definedUser,file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,@RequestBody(required = false) UserDto userDto ) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
