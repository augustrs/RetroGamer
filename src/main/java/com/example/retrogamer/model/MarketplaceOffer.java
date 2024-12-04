package com.example.retrogamer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MarketplaceOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offerId;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private MarketplaceListing listing;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double offerPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and setters

    public Integer getOfferId() {
        return offerId;
    }
    @Column(nullable = false)
    private String status; // Hvis status er en String (eller en anden type)

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public MarketplaceListing getListing() {
        return listing;
    }

    public void setListing(MarketplaceListing listing) {
        this.listing = listing;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
