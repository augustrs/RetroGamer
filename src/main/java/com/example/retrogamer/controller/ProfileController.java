package com.example.retrogamer.controller;

import com.example.retrogamer.model.User;
import com.example.retrogamer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping ("/api/users/")
public class ProfileController {
    private final UserRepository userRepository;



    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping ("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    @GetMapping ("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @PostMapping ("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userRepository.findByUserId(userId)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setBio(user.getBio());
                    existingUser.setProfilePicture(user.getProfilePicture());
                    return ResponseEntity.ok(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping ("/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        return userRepository.findByUserId(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping ("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/{userId}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        if (!file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("File is not an image");
        }
        return userRepository.findByUserId(userId)
                .map(user -> {
                    try {
                        byte[] imageBytes = file.getBytes();
                        user.setProfilePicture(imageBytes);
                        userRepository.save(user);
                        return ResponseEntity.ok("File uploaded successfully");

                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Failed to read file");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{userId}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = userOptional.get().getProfilePicture();
        if (imageBytes == null || imageBytes.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(imageBytes);
    }
    @PostMapping("/{userId}/update-bio")
    public ResponseEntity<User> updateBio(@PathVariable Long userId, @RequestBody String bio) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setBio(bio);
                    userRepository.save(user);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }







}
