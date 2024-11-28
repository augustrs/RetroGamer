package com.example.retrogamer.controller;

import com.example.retrogamer.model.MarketplaceListing;
import com.example.retrogamer.model.MarketplaceOffer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class MarketPlaceController {

    @RestController
    @RequestMapping("/api/marketplace")
    public class MarketplaceController {

        private final EntityManager entityManager;

        @Autowired
        public MarketplaceController(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        // Marketplace Listing Endpoints

        @GetMapping("/listings/{listingId}")
        public ResponseEntity<MarketplaceListing> getListingById(@PathVariable Long listingId) {
            MarketplaceListing listing = entityManager.find(MarketplaceListing.class, listingId);
            if (listing == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listing);
        }

        @GetMapping("/listings")
        public List<MarketplaceListing> getAllListings() {
            TypedQuery<MarketplaceListing> query = entityManager.createQuery("SELECT l FROM MarketplaceListing l", MarketplaceListing.class);
            return query.getResultList();
        }

        @PostMapping("/listings")
        public ResponseEntity<MarketplaceListing> createListing(@RequestBody MarketplaceListing listing) {
            entityManager.persist(listing);
            return ResponseEntity.ok(listing);
        }

        @PutMapping("/listings/{listingId}")
        public ResponseEntity<MarketplaceListing> updateListing(@PathVariable Long listingId, @RequestBody MarketplaceListing updatedListing) {
            MarketplaceListing existingListing = entityManager.find(MarketplaceListing.class, listingId);
            if (existingListing == null) {
                return ResponseEntity.notFound().build();
            }

            existingListing.setTitle(updatedListing.getTitle());
            existingListing.setDescription(updatedListing.getDescription());
            existingListing.setPrice(updatedListing.getPrice());
            existingListing.setImage(updatedListing.getImage());
            existingListing.setCategory(updatedListing.getCategory());

            entityManager.merge(existingListing);
            return ResponseEntity.ok(existingListing);
        }

        @DeleteMapping("/listings/{listingId}")
        public ResponseEntity<Void> deleteListing(@PathVariable Long listingId) {
            MarketplaceListing existingListing = entityManager.find(MarketplaceListing.class, listingId);
            if (existingListing == null) {
                return ResponseEntity.notFound().build();
            }

            entityManager.remove(existingListing);
            return ResponseEntity.noContent().build();
        }

        // Marketplace Offer Endpoints

        @GetMapping("/offers/{offerId}")
        public ResponseEntity<MarketplaceOffer> getOfferById(@PathVariable Integer offerId) {
            MarketplaceOffer offer = entityManager.find(MarketplaceOffer.class, offerId);
            if (offer == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(offer);
        }

        @GetMapping("/offers")
        public List<MarketplaceOffer> getAllOffers() {
            TypedQuery<MarketplaceOffer> query = entityManager.createQuery("SELECT o FROM MarketplaceOffer o", MarketplaceOffer.class);
            return query.getResultList();
        }

        @PostMapping("/offers")
        public ResponseEntity<MarketplaceOffer> createOffer(@RequestBody MarketplaceOffer offer) {
            entityManager.persist(offer);
            return ResponseEntity.ok(offer);
        }

        @PutMapping("/offers/{offerId}")
        public ResponseEntity<MarketplaceOffer> updateOffer(@PathVariable Integer offerId, @RequestBody MarketplaceOffer updatedOffer) {
            MarketplaceOffer existingOffer = entityManager.find(MarketplaceOffer.class, offerId);
            if (existingOffer == null) {
                return ResponseEntity.notFound().build();
            }

            existingOffer.setOfferPrice(updatedOffer.getOfferPrice());
            existingOffer.setStatus(updatedOffer.getStatus());

            entityManager.merge(existingOffer);
            return ResponseEntity.ok(existingOffer);
        }

        @DeleteMapping("/offers/{offerId}")
        public ResponseEntity<Void> deleteOffer(@PathVariable Integer offerId) {
            MarketplaceOffer existingOffer = entityManager.find(MarketplaceOffer.class, offerId);
            if (existingOffer == null) {
                return ResponseEntity.notFound().build();
            }

            entityManager.remove(existingOffer);
            return ResponseEntity.noContent().build();
        }
    }


}