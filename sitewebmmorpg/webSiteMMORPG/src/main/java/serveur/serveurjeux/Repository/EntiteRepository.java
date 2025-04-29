package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.Entite;
import serveur.serveurjeux.Entity.Personnage;

public interface EntiteRepository extends JpaRepository<Entite, Long> {
}
