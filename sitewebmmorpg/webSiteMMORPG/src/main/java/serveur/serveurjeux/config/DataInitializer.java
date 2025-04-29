package serveur.serveurjeux.config;

import serveur.serveurjeux.Entity.Caisse;
import serveur.serveurjeux.Entity.TokenPack;
import serveur.serveurjeux.Repository.CaisseRepository;
import serveur.serveurjeux.Repository.TokenRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(TokenRepository tokenRepository, CaisseRepository caisseRepository) {
        return args -> {
            if (tokenRepository.count() == 0) { // Vérifie si la table est vide
                List<TokenPack> packs = List.of(
                        new TokenPack("Pack Bronze", 100, 599),
                        new TokenPack("Pack Argent", 250, 1099),
                        new TokenPack("Pack Or", 500, 1999),
                        new TokenPack("Pack Platine", 1000, 3499),
                        new TokenPack("Pack Diamant", 2500, 7999),
                        new TokenPack("Pack Ultime", 5000, 14999)
                );
                tokenRepository.saveAll(packs);
                System.out.println("✅ Packs de tokens insérés en base de données !");
            } else {
                System.out.println("✅ Packs déjà présents en base, aucune insertion nécessaire.");
            }

            // Initialisation des caisses
            if (caisseRepository.count() == 0) {
                List<Caisse> caisses = List.of(
                        new Caisse("Caisse d’Armes",
                                "Débloque des armes puissantes pour écraser tes ennemis.",
                                50,
                                "/img/caisse-armes.png",
                                List.of(1),0, List.of(1)), // Épée (id 1)

                        new Caisse("Caisse d’Armures",
                                "Renforce ta défense avec des équipements de protection.",
                                60,
                                "/img/caisse-armures.png",
                                List.of(1, 1, 1, 1),0, List.of(4,2,5,3)), // Casque (id 1), Bottes (id 2), Gants (id 5), Plastron (id 3)

                        new Caisse("Caisse de Consommables",
                                "Prépare-toi au combat avec des potions et objets utiles.",
                                45,
                                "/img/caisse-consommables.png",
                                List.of(1,2),0, List.of(6)), // Potion (id 6)

                        new Caisse("Caisse Mystère",
                                "Un mélange aléatoire d’armes, armures et consommables.",
                                80,
                                "/img/caisse-mystere.png",
                                List.of(1, 2, 3, 5, 6),0, List.of(1)) // Tous les objets existants
                );

                caisseRepository.saveAll(caisses);
            }


        };
    }
}