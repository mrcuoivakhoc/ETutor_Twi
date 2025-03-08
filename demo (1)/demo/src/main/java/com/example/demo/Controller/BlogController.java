package com.example.demo.Controller;

import com.example.demo.Entity.Blog;
import com.example.demo.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping()
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @PostMapping("/save")
    public ResponseEntity<Blog> saveBlog(
            @ModelAttribute Blog blog,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("userId") Long userId) {

        return ResponseEntity.ok(blogService.saveBlog(blog, file, userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable Long id,
            @ModelAttribute Blog blog,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("userId") Long userId) {

        return ResponseEntity.ok(blogService.updateBlog(id, blog, file, userId));
    }




    @DeleteMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        return blogService.deleteBlog(id);
    }
}