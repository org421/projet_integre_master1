package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.ResultatOuvertureCaisse;

import java.util.List;

public interface ResultatOuvertureCaisseRepository extends JpaRepository<ResultatOuvertureCaisse, Long> {
    List<ResultatOuvertureCaisse> findByUserId(Long userId);

}
