package com.example.retrogamer.config;

import com.example.retrogamer.model.*;
import com.example.retrogamer.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class Initdata {

    @Bean
    CommandLineRunner initDataLoader(UserRepository userRepository,
                                     CategoryRepository categoryRepository,
                                     ForumPostRepository forumPostRepository,
                                     ForumLikeRepository forumLikeRepository,
                                     ForumCommentRepository forumCommentRepository) {
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

            // Create Forum Posts
            Random random = new Random();
            for (Category category : categories) {
                for (int i = 1; i <= 10; i++) {
                    ForumPost post = new ForumPost();
                    post.setTitle(generatePostTitle(category.getName(), i));
                    post.setContent(generatePostContent(category.getName(), i));
                    post.setUser(users.get(random.nextInt(users.size())));
                    post.setCategory(category);
                    post.setLikeCount(random.nextInt(50)); // Random like count
                    forumPostRepository.save(post);

                    // Optionally create random likes
                    int likeCount = post.getLikeCount();
                    for (int j = 0; j < likeCount; j++) {
                        ForumLike like = new ForumLike();
                        like.setPost(post);
                        like.setUser(users.get(random.nextInt(users.size())));
                        forumLikeRepository.save(like);
                    }

                    // Create Comments for Posts
                    int commentCount = random.nextInt(5) + 1; // Random number of comments between 1 and 5
                    for (int k = 0; k < commentCount; k++) {
                        ForumComment comment = new ForumComment();
                        comment.setPost(post);
                        comment.setUser(users.get(random.nextInt(users.size())));
                        comment.setContent(generateCommentContent(post.getTitle(), category.getName(), k));
                        forumCommentRepository.save(comment);
                    }
                }
            }
        };
    }

    private String generatePostTitle(String categoryName, int index) {
        String[] xboxTitles = {
                "The Best Halo Game Ever?", "Why Xbox Game Pass is a Game Changer", "Classic Xbox Exclusives You Must Play",
                "Tips for Getting the Most Out of Your Xbox", "The Future of Xbox Gaming", "Favorite Multiplayer Games on Xbox",
                "The Evolution of Xbox Consoles", "Underrated Xbox Titles Everyone Should Try", "Xbox vs. PlayStation: A Friendly Debate",
                "Backwards Compatibility: Xbox Does It Right"
        };
        String[] nintendoTitles = {
                "Top 10 Mario Games of All Time", "Why Zelda is a Masterpiece", "The Joy of Nintendo Switch Gaming",
                "Tips for Catching 'Em All in Pokémon", "The Most Challenging Nintendo Bosses", "Favorite Party Games on Switch",
                "Nintendo Nostalgia: SNES Classics", "Why Animal Crossing is So Relaxing", "The Legend of Metroid: A Retrospective",
                "Indie Games on Nintendo Switch You Can't Miss"
        };
        String[] playstationTitles = {
                "The Last of Us: Storytelling at Its Best", "Why PlayStation Exclusives Dominate", "The Best Graphics on PlayStation 5",
                "Tips for Trophy Hunters", "The Evolution of PlayStation Controllers", "Underrated PS4 Gems",
                "Why God of War is a Must-Play", "Top Horror Games on PlayStation", "Favorite JRPGs on PlayStation",
                "PlayStation VR: Worth the Investment?"
        };
        String[] pcTitles = {
                "Building Your First Gaming PC", "Why PC Gaming is Superior", "The Best RTS Games for PC",
                "Tips for Optimizing Your Rig", "Top PC Mods That Enhance Gameplay", "The Most Addictive PC Strategy Games",
                "Favorite FPS Games on PC", "The History of PC Gaming", "The Future of Graphics in PC Games",
                "PC vs. Console: Which is Right for You?"
        };

        switch (categoryName) {
            case "Xbox":
                return xboxTitles[index - 1];
            case "Nintendo":
                return nintendoTitles[index - 1];
            case "PlayStation":
                return playstationTitles[index - 1];
            case "PC":
                return pcTitles[index - 1];
            default:
                return "General Gaming Discussion " + index;
        }
    }

    private String generatePostContent(String categoryName, int index) {
        return "Let's dive into the world of " + categoryName + " gaming! Share your thoughts, experiences, and tips on this topic. Discussion " + index;
    }

    private String generateCommentContent(String postTitle, String categoryName, int commentIndex) {
        String[] xboxComments = {
                "I totally agree, Halo defined a generation of gamers!",
                "Game Pass has completely changed how I discover games. Love it!",
                "Backwards compatibility is such a win for Xbox players!",
                "I still remember playing the original Gears of War—amazing memories!",
                "The new Fable game looks so promising, can't wait!"
        };

        String[] nintendoComments = {
                "Mario Galaxy is my favorite, but Odyssey comes close!",
                "Zelda Breath of the Wild redefined open-world games for me.",
                "Switch is perfect for gaming on the go!",
                "Animal Crossing got me through the pandemic. So relaxing!",
                "The SNES era will always hold a special place in my heart."
        };

        String[] playstationComments = {
                "The Last of Us Part II was so emotional, loved it.",
                "God of War's combat is on another level!",
                "I spent countless hours trophy hunting on PS4.",
                "PlayStation VR is underrated for horror games!",
                "Uncharted 4 had one of the best stories I've played."
        };

        String[] pcComments = {
                "Building my first gaming PC was such a rewarding experience!",
                "Mods like these really make Skyrim a whole new game.",
                "I love the precision you get with mouse and keyboard for FPS games.",
                "The strategy games on PC are unmatched. Total War is a favorite!",
                "Upgrading my GPU made such a difference in performance."
        };

        switch (categoryName) {
            case "Xbox":
                return xboxComments[commentIndex % xboxComments.length];
            case "Nintendo":
                return nintendoComments[commentIndex % nintendoComments.length];
            case "PlayStation":
                return playstationComments[commentIndex % playstationComments.length];
            case "PC":
                return pcComments[commentIndex % pcComments.length];
            default:
                return "Great point about " + postTitle + ". I hadn't thought of it that way!";
        }
    }
}
