package serveur.serveurjeux.controller;

import serveur.serveurjeux.DTO.Entity.Monstre;
import serveur.serveurjeux.DTO.typeReponse.*;
import serveur.serveurjeux.Entity.PersonnageJoueur;
import serveur.serveurjeux.FirstScreen;
import serveur.serveurjeux.LoginListener;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.typeMessage.DemandeLogin;
import serveur.serveurjeux.ScreenManager;
import serveur.serveurjeux.informationClient.Reseau;

import java.io.IOException;

public class ActionReseau{
    private static LoginListener loginListener; // Callback pour la réponse du login

    public static void receptionUUID(ReponseUUID reponse) {
        Reseau.UUID = reponse.getUuid();
     //   System.out.println(Reseau.UUID);
    }

    public static void EnvoieLogin(String pseudo, String password) throws IOException {

       // System.out.println(pseudo);
      //  System.out.println(password);


        Envoie envoie = new Envoie(Reseau.address,Reseau.UUID);
        DemandeLogin demandeLogin = new DemandeLogin();
        demandeLogin.pseudo = pseudo;
        demandeLogin.mdp = password;
        envoie.ajouterMessage(demandeLogin);
        Reseau.writer.writeObject(envoie);
        Reseau.writer.flush();
    }

    public static void receptionLogin(ReponseDemandeLogin reponse) {
      //  System.out.println(reponse.valide);
        if (reponse.valide){
            Reseau.pseudo = reponse.pseudo;
            ScreenManager.ecranJeu = 1;
        }
        if (loginListener != null) {
            loginListener.onLoginResponse(reponse.valide);
        }
    }

    public static void receptionPersonnage(ReponseInformationPersonnage reponse) {
        //System.out.println(reponse.personnage);
        PersonnageJoueur.personnage = reponse.personnage;
    }

    public static void receptionCreationPersonnage(ReponseCreationPersonnage reponse) {
        PersonnageJoueur.personnage = reponse.personnage;
    }

    public static void receptionDeplacement(ReponseDeplacement reponse) {
        PersonnageJoueur.personnage.getEntite().setX(reponse.x);
        PersonnageJoueur.personnage.getEntite().setY(reponse.y);

    }


    public static void receptionPersonnageProche(ReponseInformationJoueurProche reponse) {
//        System.out.println(reponse.personnage);
//        System.out.println(reponse.personnage.getEntite().getX());
//        System.out.println(reponse.personnage.getEntite().getY());
//        System.out.println("Matrice");
//        System.out.println(reponse.personnage.getEntite().getxMatrice());
//        System.out.println(reponse.personnage.getEntite().getyMatrice());
        FirstScreen.personnageProche.add(reponse.personnage);
    }

    public static void receptionMonstreProche(ReponseInformationMonstre reponse) {
        Monstre monstre = new Monstre(reponse.typeMonstre, reponse.x, reponse.y);
        FirstScreen.monstreProche.add(monstre);
    }

    public static void receptionInventaire(ReponseInventaire reponse){
       // System.out.println(reponse.inventaire);
        PersonnageJoueur.inventaire = reponse.inventaire;
    }

    public static void receptionSort(ReponseAffichageSort reponse){
        FirstScreen.sortAffichage.add(reponse.sort);
    }




    public static void setLoginListener(LoginListener listener) {
        loginListener = listener;
    }


}
