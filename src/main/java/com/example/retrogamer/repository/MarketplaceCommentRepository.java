package com.example.retrogamer.repository;

import com.example.retrogamer.model.MarketplaceComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketplaceCommentRepository extends JpaRepository<MarketplaceComment, Long> {

    Optional<MarketplaceComment> findByCommentId(Long commentId);
    List<MarketplaceComment> findByListing_ListingId(Long listingId);
}
