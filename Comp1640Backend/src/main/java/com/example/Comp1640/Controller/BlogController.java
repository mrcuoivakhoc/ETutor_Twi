package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.BlogDto;
import com.example.Comp1640.DTO.ClassroomDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Service.BlogService;
import com.example.Comp1640.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping()
    public ResponseEntity<List<BlogDto>> getAllBlogDto() {
        return ResponseEntity.ok(blogService.getAllBlogDto());
    }

    @PostMapping("/save_blog")
    public ResponseEntity<BlogDto> saveNew(@RequestBody(required = false) BlogDto blogDto) {
        return ResponseEntity.ok(blogService.saveBlogDto(blogDto));
    }


}
