package serveur.serveurjeux.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.CaseInventaire;
import serveur.serveurjeux.Entity.Inventaire;

import java.util.List;

public interface CaseInventaireRepository extends JpaRepository<CaseInventaire, Long> {
    List<CaseInventaire> findByInventaire(Inventaire inventaire);

    CaseInventaire findById(long id);

}
