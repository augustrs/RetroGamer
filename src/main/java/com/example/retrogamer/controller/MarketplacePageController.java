package com.example.retrogamer.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/marketplace")
public class MarketplacePageController {

    @GetMapping("/listings")
    public String marketplacePage() {
        return "marketplace";
    }

    @GetMapping("/createListing")
    public String createListingPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "createListing";
    }
}
