package com.example.retrogamer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

    @GetMapping("/")
    public String indexPage() {
        return "redirect:/login";
    }
}
