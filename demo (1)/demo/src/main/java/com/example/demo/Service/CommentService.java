package com.example.demo.Service;

import com.example.demo.Entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    Comment saveComment(String content, Long blogId, Long userId);
    Comment updateComment(Long id, String content, Long blogId, Long userId);
    String deleteComment(Long id);
}