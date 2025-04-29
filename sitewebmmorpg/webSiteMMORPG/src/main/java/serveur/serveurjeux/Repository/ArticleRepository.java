package serveur.serveurjeux.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findFirstByTypeOrderByDateDesc(int type);

    List<Article> findAllByType(int type);
    Page<Article> findByType(int type, Pageable pageable);

    // Rechercher les articles Wiki par titre avec pagination
    Page<Article> findByTypeAndTitleContaining(int type, String title, Pageable pageable);

}
