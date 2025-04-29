package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import serveur.serveurjeux.Entity.Caisse;

@Repository
public interface CaisseRepository extends JpaRepository<Caisse, Long> {
    Caisse findByNom(String nom);
}
