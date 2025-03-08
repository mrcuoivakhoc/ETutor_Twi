package com.example.demo.Service;
import com.example.demo.Entity.Blog;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface BlogService {
    List<Blog> getAllBlogs();
    Blog saveBlog(Blog blog, MultipartFile file, Long userId);
    Blog updateBlog(Long id, Blog blog, MultipartFile file, Long userId);
    String deleteBlog(Long id);
}