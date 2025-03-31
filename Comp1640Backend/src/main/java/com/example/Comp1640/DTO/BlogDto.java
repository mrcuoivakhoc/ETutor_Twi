package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class BlogDto {

    private Long id;

    private String content;

    private UserDto userDto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public BlogDto() {}

    public BlogDto(Long id, String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.userDto = userDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BlogDto(String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.content = content;
        this.userDto = userDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.content = blog.getContent();
        this.userDto = new UserDto(blog.getUser());
        this.createdAt = blog.getCreatedAt();
        this.updatedAt = blog.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
