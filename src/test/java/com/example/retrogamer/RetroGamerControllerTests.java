package com.example.retrogamer;

import com.example.retrogamer.controller.ProfileController;
import com.example.retrogamer.model.User;
import com.example.retrogamer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RetroGamerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProfileController profileController;

    @Test
    public void testLoginSuccess() throws Exception {
        User testUser = new User();
        testUser.setEmail("test@test.com");
        testUser.setPassword("password123");

        when(userRepository.findByEmail("test@test.com")).thenReturn(testUser);

        mockMvc.perform(post("/login")
                        .param("email", "test@test.com")
                        .param("password", "password123"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    public void testDashboardWithoutAuth() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testSignupSuccess() throws Exception {
        User newUser = new User();
        newUser.setEmail("new@test.com");
        newUser.setPassword("newpass123");

        when(profileController.createUser(any(User.class)))
                .thenReturn(ResponseEntity.ok(newUser));

        mockMvc.perform(post("/signup")
                        .flashAttr("user", newUser))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login"));
    }
}