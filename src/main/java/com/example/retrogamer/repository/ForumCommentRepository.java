package com.example.retrogamer.repository;

import com.example.retrogamer.model.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForumCommentRepository extends JpaRepository<ForumComment, Long> {


    Optional<ForumComment> findByCommentId(Long commentId);

    List<ForumComment> findByPost_PostId(Long postId);}
