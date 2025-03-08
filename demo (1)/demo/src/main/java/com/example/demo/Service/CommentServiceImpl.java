package com.example.demo.Service;

import com.example.demo.Entity.Blog;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment saveComment(String content, Long blogId, Long userId) {
        Comment comment = new Comment();
        comment.setContent(content);

        Blog blog = blogRepository.findById(blogId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (blog == null || user == null) {
            throw new RuntimeException("Blog or User not found!");
        }

        comment.setBlog(blog);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, String content, Long blogId, Long userId) {
        Comment existingComment = commentRepository.findById(id).orElse(null);

        if (existingComment != null) {
            existingComment.setContent(content);

            Blog blog = blogRepository.findById(blogId).orElse(null);
            User user = userRepository.findById(userId).orElse(null);

            if (blog == null || user == null) {
                throw new RuntimeException("Blog or User not found!");
            }

            existingComment.setBlog(blog);
            existingComment.setUser(user);

            return commentRepository.save(existingComment);
        }

        return null;
    }

    @Override
    public String deleteComment(Long id) {
        commentRepository.deleteById(id);
        return "Comment deleted successfully";
    }
}
