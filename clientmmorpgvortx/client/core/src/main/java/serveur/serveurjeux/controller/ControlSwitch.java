package serveur.serveurjeux.controller;

import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.typeReponse.*;
import serveur.serveurjeux.FirstScreen;

import java.io.IOException;
import java.io.PrintWriter;

public class ControlSwitch {

    public static void lecture(int code, Message message) throws IOException {
        switch (code) {
            case 1: //message de ping
                break;
            case 2: //reception de UUID
                System.out.println("requête de UUID");
                ActionReseau.receptionUUID((ReponseUUID) message);
                break;
            case 3: //reception du pseudo ainsi que le mdp
                System.out.println("reception du login");
                ActionReseau.receptionLogin((ReponseDemandeLogin) message);
                break;
            case 5:
                System.out.println("reception du personnage");
                ActionReseau.receptionPersonnage((ReponseInformationPersonnage) message);
                break;
            case 7:
                ActionReseau.receptionCreationPersonnage((ReponseCreationPersonnage) message);
                break;
            case 8:
                ActionReseau.receptionDeplacement((ReponseDeplacement) message);
                break;
            case 10:
                ActionReseau.receptionPersonnageProche((ReponseInformationJoueurProche) message);
                break;
            case 11:
                FirstScreen.personnageProche.clear();
                FirstScreen.monstreProche.clear();
                FirstScreen.sortAffichage.clear();
                //System.out.println("TOUT EST CLEARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                break;
            case 13:
                ActionReseau.receptionInventaire((ReponseInventaire) message);
                break;
            case 14:
                ActionReseau.receptionSort((ReponseAffichageSort) message);
                break;
            case 15:
                ActionReseau.receptionMonstreProche((ReponseInformationMonstre)  message);
                break;
            default:
                System.out.println("Erreur lecture");
                break;
        }
    }


}
