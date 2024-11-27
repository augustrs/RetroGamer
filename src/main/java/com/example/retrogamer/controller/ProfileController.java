package com.example.retrogamer.controller;

import com.example.retrogamer.model.User;
import com.example.retrogamer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping ("/api/users/")
public class ProfileController {
    private final UserRepository userRepository;



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


}
