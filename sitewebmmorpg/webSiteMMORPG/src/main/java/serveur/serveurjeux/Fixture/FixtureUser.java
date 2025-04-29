package serveur.serveurjeux.Fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.UserRepository;

import java.util.ArrayList;
import java.util.Random;

@Component
public class FixtureUser implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.deleteAll();
            User user = new User();
            user.setEnabled(true);
            user.setRole("ROLE_ADMIN");
            user.setMdp("$2a$10$J5IYn7kIToQIy2gIupv1YOiYOmxcyx1YlrQRerQK1CNP85Mk.S/j2");
            user.setUsername("Cyprien");
            user.setFactures(new ArrayList<>());
            user.setEmail("cyp.muyard@gmail.com");
            user.setTickets(new ArrayList<>());
            userRepository.save(user);

            User user1 = new User();
            user1.setEnabled(true);
            user1.setRole("ROLE_USER");
            user1.setMdp("$2a$10$J5IYn7kIToQIy2gIupv1YOiYOmxcyx1YlrQRerQK1CNP85Mk.S/j2");
            user1.setUsername("Ozan");
            user1.setFactures(new ArrayList<>());
            user1.setEmail("ozangunes42150@gmail.com");
            user1.setTickets(new ArrayList<>());
            userRepository.save(user1);

            User user2 = new User();
            user2.setEnabled(true);
            user2.setRole("ROLE_USER");
            user2.setMdp("$2a$10$J5IYn7kIToQIy2gIupv1YOiYOmxcyx1YlrQRerQK1CNP85Mk.S/j2");
            user2.setUsername("Tolga");
            user2.setFactures(new ArrayList<>());
            user2.setEmail("y1903tolga@gmail.com");
            user2.setTickets(new ArrayList<>());
            userRepository.save(user2);

            User user3 = new User();
            user3.setEnabled(true);
            user3.setRole("ROLE_SUPER_ADMIN");
            user3.setMdp("$2a$10$J5IYn7kIToQIy2gIupv1YOiYOmxcyx1YlrQRerQK1CNP85Mk.S/j2");
            user3.setUsername("lobe");
            user3.setFactures(new ArrayList<>());
            user3.setEmail("ecrard2715@gmail.com");
            user3.setTickets(new ArrayList<>());
            userRepository.save(user3);

            for (int i = 1; i <= 200; i++) {
                user = new User();
                user.setEnabled(true);
                user.setRole("ROLE_USER");
                user.setMdp("$2a$10$J5IYn7kIToQIy2gIupv1YOiYOmxcyx1YlrQRerQK1CNP85Mk.S/j2");
                user.setUsername("TestUser" + i);
                user.setFactures(new ArrayList<>());
                user.setEmail("TestUser"+i+"@gmail.com");
                user.setTickets(new ArrayList<>());
                userRepository.save(user);
            }
        }
    }
}
