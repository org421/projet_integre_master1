package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.Personnage;

public interface PersonnageRepository extends JpaRepository<Personnage, Long> {
}
