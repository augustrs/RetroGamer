package com.example.retrogamer.controller;

import com.example.retrogamer.model.ForumComment;
import com.example.retrogamer.repository.ForumCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final ForumCommentRepository forumCommentRepository;

    public CommentController(ForumCommentRepository forumCommentRepository) {
        this.forumCommentRepository = forumCommentRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<ForumComment> createComment(@RequestBody ForumComment forumComment) {
        return ResponseEntity.ok(forumCommentRepository.save(forumComment));
    }

    @GetMapping("/{postId}")
    public ForumComment getCommentByPostId(@PathVariable Long postId) {
        return forumCommentRepository.findByPost_PostId(postId).orElse(null);
    }

    @PostMapping ("/delete/{commentId}")
    public ResponseEntity<ForumComment> deleteComment(@PathVariable Long commentId) {
        return forumCommentRepository.findByCommentId(commentId)
                .map(comment -> {
                    forumCommentRepository.delete(comment);
                    return ResponseEntity.ok(comment);
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
