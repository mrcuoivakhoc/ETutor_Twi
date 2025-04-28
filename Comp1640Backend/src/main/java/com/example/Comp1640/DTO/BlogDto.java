package com.example.Comp1640.DTO;

import com.example.Comp1640.Entity.Blog;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class BlogDto {

    private Long id;
    private String content;
    private UserDto userDto;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String fileName;
    private String fileType;
    private byte[] fileData;

    public BlogDto() {}

    public BlogDto(Long id, String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime updatedAt, String fileName, String fileType, byte[] fileData) {
        this.id = id;
        this.content = content;
        this.userDto = userDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
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
        this.fileName = blog.getFileName();
        this.fileType = blog.getFileType();
        this.fileData = blog.getFileData();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ✅ Ẩn các trường không muốn trả về bằng @JsonIgnore
    @JsonIgnore
    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    @JsonIgnore
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonIgnore
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @JsonIgnore
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
