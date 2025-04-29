package serveur.serveurjeux.Fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import serveur.serveurjeux.Entity.Category;
import serveur.serveurjeux.Entity.Topic;
import serveur.serveurjeux.Repository.CategoryRepository;
import serveur.serveurjeux.Repository.TopicRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@Component
@Order(2)
public class FixtureTopic implements CommandLineRunner {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TopicRepository topicRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static String generateRandomTitle() {
        return generateRandomString(10);
    }

    public static String generateRandomContent() {
        return generateRandomString(50);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(topicRepository.count() == 0) {
            for(int i = 0; i < 20; i++){
                Category category = categoryRepository.findById(i % 3);
                if(category != null) {
                    category.getTopics().size();
                    Topic topic = new Topic();
                    topic.setViews(i * i);
                    topic.setCategory(category);
                    category.getTopics().add(topic);
                    topic.setTitle(generateRandomTitle());
                    topic.setContent(generateRandomContent());
                    topic.setCreatedAt(LocalDateTime.now());
                    topic.setReplies(new ArrayList<>());
                }
            }
        }
    }
}
