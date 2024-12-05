package com.example.retrogamer.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfilePageController {

    @GetMapping("/profile")
    public String profilePage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "user";
    }

    @GetMapping("/profile/{userId}")
    public String profileWithId(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "user";
    }

    @GetMapping("/create")
    public String createProfile(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "createuser";
    }

    @GetMapping("/upload-pfp")
    public String uploadPfp(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "upload";
    }
}
