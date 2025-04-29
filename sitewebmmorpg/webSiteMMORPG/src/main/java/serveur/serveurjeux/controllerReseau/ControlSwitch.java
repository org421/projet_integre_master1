package serveur.serveurjeux.controllerReseau;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.typeReponse.*;

import java.io.IOException;

@Service
public class ControlSwitch {

    @Autowired
    ActionReseau actionReseau;

    public void lecture(int code, Message message) throws IOException {

        switch (code) {
            case 1: //message de ping
                break;
            case 2: //reception de UUID
                System.out.println("requête de UUID");
                actionReseau.receptionUUID((ReponseUUID) message);
                break;
            case 4: //demande d'information de login
                actionReseau.demandeInformationLogin((ReponseDemandeLoginSiteWeb) message);
                break;
            case 6: // reception de nombre de joueurs en ligne
                System.out.println("requete pour nombre joueur sur le jeu");
                actionReseau.receptionNbJoueur((ReponseNbJoueurs) message);
                break;
            case 7: //demande creation personnage
                actionReseau.demandeCreationPersonnage((ReponseCreationPersonnage) message);
                break;
            case 12: //sauvegarde joueur
                actionReseau.sauvegardePersonnage((ReponseSauvegardePersonnage) message);
                break;
            default:
                System.out.println("Erreur lecture");
                break;
        }
    }


}
