package com.example.retrogamer.controller;

import com.example.retrogamer.model.Category;
import com.example.retrogamer.model.ForumPost;
import com.example.retrogamer.model.User;
import com.example.retrogamer.repository.CategoryRepository;
import com.example.retrogamer.repository.ForumPostRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumPostRepository forumPostRepository;
    private final CategoryRepository categoryRepository;

    public ForumController(ForumPostRepository forumPostRepository, CategoryRepository categoryRepository) {
        this.forumPostRepository = forumPostRepository;
        this.categoryRepository = categoryRepository;
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

}
