package com.example.retrogamer.controller;

import com.example.retrogamer.model.MarketplaceComment;
import com.example.retrogamer.repository.MarketplaceCommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketplacecomment")
public class MarketplaceCommentController {

    private MarketplaceCommentRepository marketplaceCommentRepository;

    public MarketplaceCommentController(MarketplaceCommentRepository marketplaceCommentRepository) {
        this.marketplaceCommentRepository = marketplaceCommentRepository;
    }

    @GetMapping("/{listingId}")
    public List<MarketplaceComment> getCommentByListingId(@PathVariable Long listingId) {
        return marketplaceCommentRepository.findByListing_ListingId(listingId);
    }

    @PostMapping("/create")
    public ResponseEntity<MarketplaceComment> createComment(@RequestBody MarketplaceComment marketplaceComment) {
        System.out.println(marketplaceComment);
        return ResponseEntity.ok(marketplaceCommentRepository.save(marketplaceComment));
    }

    @PostMapping ("/delete/{commentId}")
    public ResponseEntity<MarketplaceComment> deleteComment(@PathVariable Long commentId) {
        return marketplaceCommentRepository.findByCommentId(commentId)
                .map(comment -> {
                    marketplaceCommentRepository.delete(comment);
                    return ResponseEntity.ok(comment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
