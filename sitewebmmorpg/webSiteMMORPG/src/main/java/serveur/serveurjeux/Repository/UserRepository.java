package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.Personnage;
import serveur.serveurjeux.Entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findById(long id);
    List<User> findByRole(String role);
    List<User> findByPersonnageIsNotNull();
    ;

    User findByusernameAndMdp(String username, String password);
}
