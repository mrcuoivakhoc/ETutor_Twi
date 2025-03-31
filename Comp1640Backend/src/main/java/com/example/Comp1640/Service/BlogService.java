package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.BlogDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.Blog;

import java.util.List;

public interface BlogService {

    List<BlogDto>  getAllBlogDto();

    BlogDto saveBlogDto(BlogDto blogDto);

    String deleteBlogDto(Long id);
}
