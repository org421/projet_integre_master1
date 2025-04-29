package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.Query;
import serveur.serveurjeux.Entity.Category;
import serveur.serveurjeux.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByCategory(Category category);

    void deleteAllByCategoryId(Long idCategory);
    List<Topic> findByCategoryId(Long categoryId);
    List<Topic> findTop3ByOrderByViewsDesc();

    @Query("SELECT t FROM Topic t ORDER BY SIZE(t.replies) DESC")
    List<Topic> findTop3ByMostReplies();
}

