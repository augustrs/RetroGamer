package com.example.retrogamer.repository;

import com.example.retrogamer.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumPostRepository extends JpaRepository<ForumPost,Long> {
    Optional<ForumPost> findByPostId(Long postId);

}
