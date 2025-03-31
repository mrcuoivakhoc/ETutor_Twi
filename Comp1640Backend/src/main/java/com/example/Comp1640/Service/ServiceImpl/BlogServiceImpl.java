package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.BlogDto;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.Entity.Blog;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Repository.BlogRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BlogDto> getAllBlogDto() {
        List<Blog> listBlogs = blogRepository.findAll();
        List<BlogDto> listBlogDto = new ArrayList<>();

        if (listBlogs.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < listBlogs.size(); i++){
//                System.out.println(listBlogs.get(i).getUser().getId() + "hanoi");
                BlogDto blogDto = new BlogDto(listBlogs.get(i));
                listBlogDto.add(blogDto);
            }
        }
        return listBlogDto;
    }

    @Override
    public BlogDto saveBlogDto(BlogDto blogDto) {
        if(blogDto == null){
            return null;
        }else {
            Blog blog = new Blog(blogDto.getContent(), userRepository.findById(blogDto.getUserDto().getId()).get());
            blogRepository.save(blog);
            return blogDto;
        }
    }

    @Override
    public String deleteBlogDto(Long id) {

        blogRepository.deleteById(id);

        return "Deleted Blog Successfully";
    }


}
