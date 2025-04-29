package serveur.serveurjeux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import serveur.serveurjeux.Entity.Reply;
import serveur.serveurjeux.Entity.Topic;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByTopic(Topic topic);

}
