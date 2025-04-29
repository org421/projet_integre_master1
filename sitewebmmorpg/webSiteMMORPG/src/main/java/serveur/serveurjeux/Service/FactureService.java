package serveur.serveurjeux.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serveur.serveurjeux.Entity.Facture;
import serveur.serveurjeux.Entity.TokenPack;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.FactureRepository;
import serveur.serveurjeux.Repository.TokenRepository;
import serveur.serveurjeux.Repository.UserRepository;

import java.util.List;

@Service
public class FactureService {
    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenPackRepository;

    public Facture creerFacture(Long userId, Long packId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        TokenPack pack = tokenPackRepository.findById(packId).orElseThrow(() -> new RuntimeException("Pack introuvable"));

        Facture facture = new Facture(user, pack);
        return factureRepository.save(facture);
    }

    public List<Facture> getFacturesParUtilisateur(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return factureRepository.findByClientId(user.getId());
    }
}

