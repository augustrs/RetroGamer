package com.example.retrogamer.config;

import com.example.retrogamer.model.*;
import com.example.retrogamer.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class Initdata {

    @Bean
    CommandLineRunner initDataLoader(UserRepository userRepository,
                                     CategoryRepository categoryRepository,
                                     ForumPostRepository forumPostRepository) {
        return args -> {
            // Create Users
            List<User> users = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setEmail("user" + i + "@example.com");
                user.setPassword("password" + i);
                user.setBio("This is bio for user" + i);
                users.add(user);
            }
            userRepository.saveAll(users);

            // Create Categories
            Category xbox = new Category();
            xbox.setName("Xbox");

            Category nintendo = new Category();
            nintendo.setName("Nintendo");

            Category playstation = new Category();
            playstation.setName("PlayStation");

            Category pc = new Category();
            pc.setName("PC");

            List<Category> categories = List.of(xbox, nintendo, playstation, pc);
            categoryRepository.saveAll(categories);

            // Create Forum Posts and Comments
            Random random = new Random();
            String[] titles = {
                    "Xbox Tips and Tricks", "Hidden Gems on Xbox", "Why Xbox Rules the Lounge", "Top Xbox Multiplayer Games", "Xbox Graphics Revolution",
                    "Nintendo Classics Revisited", "Switch Hacks", "Legend of Zelda Insights", "Mario Kart Tips", "Pokemon Strategies",
                    "PlayStation Exclusives", "PlayStation 5 Insights", "Best PlayStation Controllers", "PlayStation VR Updates", "Why PlayStation is King",
                    "Top PC Games", "PC Gaming Setup Ideas", "Best PC RPGs", "Top FPS Games on PC", "Ultimate Modding Tips"
            };

            String[] contents = {
                    "What are your favorite Xbox features?",
                    "Exploring hidden gems on Xbox that everyone should try.",
                    "Why do you think Xbox dominates the living room?",
                    "Discuss your favorite multiplayer games on Xbox.",
                    "Xbox has set a new standard in graphics. Agree?",
                    "Let’s talk about the best Nintendo classics and why they matter today!",
                    "Share your favorite Switch hacks and tips!",
                    "What are your thoughts on the latest Zelda game?",
                    "Let’s dive into Mario Kart strategies.",
                    "Which Pokemon game do you think is the most strategic?",
                    "Share your thoughts on the latest PlayStation exclusives.",
                    "What do you love most about the PlayStation 5?",
                    "Are third-party PlayStation controllers worth it?",
                    "Discuss the latest updates in PlayStation VR technology.",
                    "What makes PlayStation the top console for gamers?",
                    "Discuss the top PC games to play in 2024!",
                    "Let’s exchange ideas for ultimate PC gaming setups!",
                    "What are your favorite PC RPG games?",
                    "Which FPS game has the best mechanics on PC?",
                    "Tips for modding games to enhance gameplay experience."
            };

            List<byte[]> images = List.of(
                    loadImageBytes("post1.jpg"),
                    loadImageBytes("post2.jpg"),
                    loadImageBytes("post3.jpg")
            );

            for (int catIdx = 0; catIdx < categories.size(); catIdx++) {
                Category category = categories.get(catIdx);
                for (int i = 0; i < 5; i++) {
                    ForumPost post = new ForumPost();
                    post.setTitle(titles[catIdx * 5 + i]);
                    post.setContent(contents[catIdx * 5 + i]);
                    post.setUser(users.get(random.nextInt(users.size())));
                    post.setCategory(category);
                    post.setLikeCount(random.nextInt(50));
                    post.setImage(images.get(random.nextInt(images.size())));

                    forumPostRepository.save(post);
                }
            }
        };
    }

    private byte[] loadImageBytes(String fileName) {
        try {
            return Files.readAllBytes(new ClassPathResource("images/" + fileName).getFile().toPath());
        } catch (IOException e) {
            System.err.println("Error loading image " + fileName + ": " + e.getMessage());
            return null;
        }
    }
}
