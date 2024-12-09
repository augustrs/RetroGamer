package com.example.retrogamer.controller;

import com.example.retrogamer.model.User;
import com.example.retrogamer.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final ProfileController profileController;

    public UserController(UserRepository userRepository, ProfileController profileController) {
        this.userRepository = userRepository;
        this.profileController = profileController;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }


    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String singupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute User user, Model model) {

        ResponseEntity<User> response = profileController.createUser(user);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("Account created successfully. Please log in");
            return "redirect:/login";
        } else {
            model.addAttribute("error, Please try agian.");
            return "signup";
        }
    }

    @GetMapping("/signout")
    public String signout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("message", "You have been successfully signed out.");
        return "redirect:/login";
    }

}
