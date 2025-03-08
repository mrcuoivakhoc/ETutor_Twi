package com.example.demo.Service;

import com.example.demo.Entity.Blog;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BlogRepository;
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
import java.util.UUID;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog saveBlog(Blog blog, MultipartFile file, Long userId) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                blog.setMediaFile(fileName);
            }

            // Link user
            User user = userRepository.findById(userId).orElse(null);
            blog.setUser(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog, MultipartFile file, Long userId) {
        Blog existingBlog = blogRepository.findById(id).orElse(null);
        if (existingBlog != null) {
            existingBlog.setTitle(blog.getTitle());
            existingBlog.setContent(blog.getContent());

            if (file != null && !file.isEmpty()) {
                try {
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir).resolve(fileName);
                    Files.write(filePath, file.getBytes());
                    existingBlog.setMediaFile(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Link the user to the blog
            User user = userRepository.findById(userId).orElse(null);
            existingBlog.setUser(user);

            return blogRepository.save(existingBlog);
        }
        return null;
    }

    @Override
    public String deleteBlog(Long id) {
        blogRepository.deleteById(id);
        return "Blog deleted successfully";
    }
}
