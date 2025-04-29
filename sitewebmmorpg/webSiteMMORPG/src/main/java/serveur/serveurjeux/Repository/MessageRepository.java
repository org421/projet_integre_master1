package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serveur.serveurjeux.Entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
