package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import serveur.serveurjeux.Entity.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
}
