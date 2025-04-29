package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.Inventaire;

public interface InventaireRepository extends JpaRepository<Inventaire, Long> {
}
