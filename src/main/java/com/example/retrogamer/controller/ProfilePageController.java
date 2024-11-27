package com.example.retrogamer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfilePageController {

    @GetMapping("/profile")
    public String profilePage() {
        return "user";
    }

    @GetMapping("/profile/{userId}")
    public String profileWithId() {
        return "user";
    }
}
