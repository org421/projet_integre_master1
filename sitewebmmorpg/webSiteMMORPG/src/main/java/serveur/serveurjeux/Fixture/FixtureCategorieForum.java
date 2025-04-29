package serveur.serveurjeux.Fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import serveur.serveurjeux.Entity.Category;
import serveur.serveurjeux.Repository.CategoryRepository;

import java.util.ArrayList;

@Component
@Order(1)
public class FixtureCategorieForum implements CommandLineRunner {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category cat1 = new Category();
            cat1.setName("Premier pas");
            cat1.setNumCategorie(2);
            cat1.setDescription("Aide au joueur débutant");
            cat1.setTopics(new ArrayList<>());
            categoryRepository.save(cat1);

            Category cat2 = new Category();
            cat2.setName("Aide pour boss");
            cat2.setNumCategorie(2);
            cat2.setDescription("Tu es bloquer sur un boss? trouve ton bonheur ici !");
            cat2.setTopics(new ArrayList<>());
            categoryRepository.save(cat2);


            Category cat3 = new Category();
            cat3.setName("Report de bug");
            cat3.setNumCategorie(1);
            cat3.setDescription("Tu as trouver un bug ?");
            cat3.setTopics(new ArrayList<>());
            categoryRepository.save(cat3);

            Category cat4 = new Category();
            cat4.setName("Discution general");
            cat4.setNumCategorie(0);
            cat4.setDescription("Juste pour rencontrer des gens");
            cat4.setTopics(new ArrayList<>());
            categoryRepository.save(cat4);

        }
    }
}