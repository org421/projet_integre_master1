package serveur.serveurjeux.Controller;

import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import serveur.serveurjeux.Component.Reseau;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.typeMessage.DemandeNbJoueurs;
import serveur.serveurjeux.Entity.StatAdmin;
import serveur.serveurjeux.Repository.UserRepository;

import java.io.IOException;


@Controller
public class StatController {

    @Autowired
    private  UserRepository userRepository;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/statistiques", method = RequestMethod.GET)
    public String pageStat(Model m, Authentication authentication) {

        m.addAttribute("content", "statistiques");
        m.addAttribute("TotalJoueursInscrits", (int) userRepository.count());
        m.addAttribute("NbJoueurs", StatAdmin.NbJoueurs);
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_SUPER_ADMIN"))) {
            m.addAttribute("NbAdminsConnectes", userRepository.findByRole("ROLE_ADMIN").size());
        }
        Envoie envoie = new Envoie(Reseau.ip,Reseau.UUID);
        DemandeNbJoueurs demandeNbJoueurs = new DemandeNbJoueurs();
        envoie.ajouterMessage(demandeNbJoueurs);
        try {
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
            Reseau.writer.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "adminBase";
    }


}
