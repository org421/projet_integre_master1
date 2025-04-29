package serveur.serveurjeux.Controller;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.typeMessage.*;
import serveur.serveurjeux.Entity.Client;

import java.io.IOException;
import java.io.PrintWriter;

public class ControlSwitch {

    public static void lecture(int code, Message message, Client client, Envoie envoie) throws IOException {
        switch (code) {
            case 1: //message de ping
                PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
                writer.println("PONG");
                break;
            case 2: //message de demande de UUID
                ActionReseau.EnvoieUUID(client);
                break;
            case 3: //message identification siteWeb
                ActionReseau.IdentificationSiteWeb(client);
                break;
            case 4: //ActionLogin
                ActionReseau.DemandeLogin(client, (DemandeLogin) message);
                break;
            case 5: //reponse du login du site web
                ActionReseau.DemandeLoginSiteWeb(client, (DemandeLoginSiteWeb) message);
                break;
            case 6: //demande info nombre de joueur co
                ActionReseau.DemandeInfoNbJoueur(client);
                break;
            case 7:
                ActionReseau.CreationPersonnage(client, (DemandeCreationPersonnage) message, envoie);
                break;
            case 8: //demande de déplacement
                ActionReseau.DemandeDeplacement(client, (DemandeDeplacement) message);
                break;
            case 9://demande changement inventaire
                ActionReseau.ChangementCaseInventaire((DemandeChangementCaseInventaire) message,client);
                break;
            case 10:
                Deplacement.envoieInformationJoueurProche(client);
                break;
            case 13: //utilisation d'un objet ou d'une compétence
                ActionReseau.utilisationCase((DemandeCaseSelection) message,client);
                break;
            default:
                System.out.println("Erreur lecture");
                break;
        }
    }

}
