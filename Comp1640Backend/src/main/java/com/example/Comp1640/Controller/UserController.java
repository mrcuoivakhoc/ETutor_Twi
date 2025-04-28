package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.DefinedUser;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.UserDto;
import com.example.Comp1640.DTO.WbsChatMessage;
import com.example.Comp1640.Service.ChatMessageService;
import com.example.Comp1640.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUserDto() {
        return ResponseEntity.ok(userService.getAllUserDto());
    }

    @PostMapping("/save_user")
    public ResponseEntity<UserDto> saveNew(@RequestBody DefinedUser definedUser) throws IOException {
        return ResponseEntity.ok(userService.saveUser(definedUser, null));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,@RequestBody(required = false) UserDto userDto ) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/{user1Id}/{user2Id}")
    public List<WbsChatMessage> getOldMessages(@PathVariable Long user1Id,
                                                               @PathVariable Long user2Id
    ) {
        return chatMessageService.getOldMessages(user1Id,user2Id);
    }




}
