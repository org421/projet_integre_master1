package serveur.serveurjeux.Repository;

import serveur.serveurjeux.Entity.TokenPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenPack, Long> {
    TokenPack findByName(String name);
}
