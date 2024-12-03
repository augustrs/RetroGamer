package com.example.retrogamer.controller;

import com.example.retrogamer.model.Category;
import com.example.retrogamer.model.MarketplaceListing;
import com.example.retrogamer.model.MarketplaceOffer;
import com.example.retrogamer.model.User;
import com.example.retrogamer.repository.CategoryRepository;
import com.example.retrogamer.repository.MarketplaceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class MarketPlaceController {

    @RestController
    @RequestMapping("/api/marketplace")
    public class MarketplaceController {

        private MarketplaceRepository marketplaceRepository;
        private CategoryRepository categoryRepository;

        public MarketplaceController(MarketplaceRepository marketplaceRepository, CategoryRepository categoryRepository) {
            this.marketplaceRepository = marketplaceRepository;
            this.categoryRepository = categoryRepository;
        }

        @GetMapping("/listings")
        public List<MarketplaceListing> getListings() {
            return marketplaceRepository.findAll();
        }

        @GetMapping("/categories")
        public ResponseEntity<?> getCategories() {
            return ResponseEntity.ok(categoryRepository.findAll());
        }

        @PostMapping("/create")
        public ResponseEntity<?> createListing(
                @RequestParam String title,
                @RequestParam String description,
                @RequestParam Long categoryId,
                @RequestParam Double price,
                @RequestParam(required = false) MultipartFile image,
                HttpSession session) throws IOException {

            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401).body("User not logged in.");
            }

            MarketplaceListing listing = new MarketplaceListing();
            listing.setTitle(title);
            listing.setDescription(description);
            listing.setPrice(price);
            listing.setUser(user);

            Category category = categoryRepository.findById(categoryId).orElse(null);
            listing.setCategory(category);

            if (image != null && !image.isEmpty()) {
                listing.setImage(image.getBytes());
            }

            marketplaceRepository.save(listing);
            return ResponseEntity.ok("Listing created.");
        }

        @PostMapping("/delete")
        public ResponseEntity<?> deleteListing(
                @RequestParam Long id,
                HttpSession session) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401).body("User not logged in.");
            }

            MarketplaceListing listing = marketplaceRepository.findById(id).orElse(null);
            if (listing == null) {
                return ResponseEntity.status(404).body("Listing not found.");
            }

            if (!listing.getUser().getUserId().equals(user.getUserId())) {
                return ResponseEntity.status(403).body("User does not own this listing.");
            }

            marketplaceRepository.delete(listing);
            return ResponseEntity.ok("Listing deleted.");
        }
    }
}