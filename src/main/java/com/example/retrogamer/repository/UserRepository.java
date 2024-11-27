package com.example.retrogamer.repository;

import com.example.retrogamer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userid);

    Optional<User> findByUsername(String username);
    User findByEmail(String email);
}
