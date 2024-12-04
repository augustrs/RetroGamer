package com.example.retrogamer.controller;

import com.example.retrogamer.model.Category;
import com.example.retrogamer.model.ForumLike;
import com.example.retrogamer.model.ForumPost;
import com.example.retrogamer.model.User;
import com.example.retrogamer.repository.CategoryRepository;
import com.example.retrogamer.repository.ForumLikeRepository;
import com.example.retrogamer.repository.ForumPostRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumPostRepository forumPostRepository;
    private final CategoryRepository categoryRepository;
    private final ForumLikeRepository forumLikeRepository;

    public ForumController(ForumPostRepository forumPostRepository, CategoryRepository categoryRepository, ForumLikeRepository forumLikeRepository) {
        this.forumPostRepository = forumPostRepository;
        this.categoryRepository = categoryRepository;
        this.forumLikeRepository = forumLikeRepository;
    }

    @GetMapping("/posts")
    public List<ForumPost> getPosts() {
        return forumPostRepository.findAll();
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image,
            HttpSession session) throws IOException {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("User not logged in.");
        }

        ForumPost post = new ForumPost();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);

        Category category = categoryRepository.findById(categoryId).orElse(null);
        post.setCategory(category);

        if (image != null && !image.isEmpty()) {
            post.setImage(image.getBytes());
        }

        forumPostRepository.save(post);
        return ResponseEntity.ok("Post created successfully!");
    }

    @GetMapping("/post/{id}/image")
    public ResponseEntity<?> getPostImage(@PathVariable Long id) {
        ForumPost post = forumPostRepository.findById(id).orElse(null);
        if (post == null || post.getImage() == null) {
            return ResponseEntity.status(404).body("Image not found.");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(post.getImage());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        ForumPost post = forumPostRepository.findById(id).orElse(null);
        if (post == null) {
            return ResponseEntity.status(404).body("Post not found.");
        }
        return ResponseEntity.ok(post);
    }

    // Add a like to a post or remove it if already liked
    @PostMapping("/post/{postId}/like")
    @Transactional
    public ResponseEntity<Map<String, Object>> likePost(@PathVariable Long postId, HttpSession session) {
        // Retrieve logged-in user from the session
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);
        }

        // Find the post
        ForumPost post = forumPostRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.status(404).body(null);
        }

        // Check if the user already liked this post
        boolean alreadyLiked = forumLikeRepository.existsByPostAndUser(post, currentUser);

        boolean isNowLiked = !alreadyLiked; // Will be true if we're adding a like, false if removing

        if (alreadyLiked) {
            // Remove the like
            forumLikeRepository.deleteByPostAndUser(post, currentUser);
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            // Add a like
            ForumLike like = new ForumLike();
            like.setPost(post);
            like.setUser(currentUser);
            forumLikeRepository.save(like);

            post.setLikeCount(post.getLikeCount() + 1);
        }

        // Save the updated post
        forumPostRepository.save(post);

        // Create response map
        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", post.getLikeCount());
        response.put("isLiked", isNowLiked);

        return ResponseEntity.ok(response);
    }

@GetMapping("/user-likes")
    public ResponseEntity<?> getUserLikes(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);
        }

        List<ForumLike> likes = forumLikeRepository.findByUser(currentUser);
        List<ForumPost> likedPosts = likes.stream()
                .map(ForumLike::getPost)
                .collect(Collectors.toList());

        return ResponseEntity.ok(likedPosts);
    }





}
