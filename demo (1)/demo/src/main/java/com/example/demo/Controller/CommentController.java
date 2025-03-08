package com.example.demo.Controller;

import com.example.demo.Entity.Comment;
import com.example.demo.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping()
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @PostMapping("/save")
    public ResponseEntity<Comment> saveComment(
            @RequestParam("content") String content,
            @RequestParam("blogId") Long blogId,
            @RequestParam("userId") Long userId) {

        return ResponseEntity.ok(commentService.saveComment(content, blogId, userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long id,
            @RequestParam("content") String content,
            @RequestParam("blogId") Long blogId,
            @RequestParam("userId") Long userId) {

        return ResponseEntity.ok(commentService.updateComment(id, content, blogId, userId));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}