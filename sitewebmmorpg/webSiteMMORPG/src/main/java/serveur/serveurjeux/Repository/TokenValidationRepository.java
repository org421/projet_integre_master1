package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.TokenValidation;

public interface TokenValidationRepository extends JpaRepository<TokenValidation, Long> {
    TokenValidation findByToken(String token);
}
