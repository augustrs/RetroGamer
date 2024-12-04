package com.example.retrogamer.repository;

import com.example.retrogamer.model.ForumLike;
import com.example.retrogamer.model.ForumPost;
import com.example.retrogamer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForumLikeRepository extends JpaRepository<ForumLike, Long> {
    boolean existsByPostAndUser(ForumPost post, User user);

    void deleteByPostAndUser(ForumPost post, User user);

    List<ForumLike> findByUser(User user);

}

