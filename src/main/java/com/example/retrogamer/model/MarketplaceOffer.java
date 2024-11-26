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
}

