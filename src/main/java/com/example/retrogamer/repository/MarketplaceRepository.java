package com.example.retrogamer.repository;

import com.example.retrogamer.model.MarketplaceListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<MarketplaceListing, Long> {
}
