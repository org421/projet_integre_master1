package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.Facture;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByClientId(Long clientId);
}
