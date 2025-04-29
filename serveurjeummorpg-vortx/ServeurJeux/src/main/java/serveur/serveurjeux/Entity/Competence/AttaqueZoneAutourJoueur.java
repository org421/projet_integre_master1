package serveur.serveurjeux.Entity.Competence;

import serveur.serveurjeux.Controller.GestionCompetence;
import serveur.serveurjeux.Controller.GestionEffets;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.Entity.Case;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.Effet.ApplicationEffet;
import serveur.serveurjeux.Entity.Effet.ApplicationEffetMonstre;
import serveur.serveurjeux.Entity.MapCase;
import serveur.serveurjeux.Entity.NPC.Monstre;

import java.util.HashSet;
import java.util.Set;

public class AttaqueZoneAutourJoueur implements Runnable {
    private final Competence competence;

    public AttaqueZoneAutourJoueur(Competence competence) {
        this.competence = competence;
    }

    @Override
    public void run() {
        Personnage personnage = competence.client.personnage;
        int startX = personnage.getEntite().getxMatrice();
        int startY = personnage.getEntite().getyMatrice();
        int rayon = competence.getRayon();
        int duree = competence.getDuree();

        System.out.println("AttaqueZoneAutourJoueur: Déclenchement autour du joueur (" + startX + ", " + startY + ") avec rayon: " + rayon);

        // Ensemble pour stocker les joueurs affectés
        Set<Client> clientsTouches = new HashSet<>();

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < duree) {
            // Parcours des cases autour du joueur
            for (int dx = -rayon; dx <= rayon; dx++) {
                for (int dy = -rayon; dy <= rayon; dy++) {
                    int currentX = startX + dx;
                    int currentY = startY + dy;


                    // Récupération de la case
                    Case currentCase = MapCase.cases.get(currentY).get(currentX);
                    competence.positionX = currentX;
                    competence.positionY = currentY;

                    System.out.println("AttaqueZoneAutourJoueur: Vérification de la case (" + currentX + ", " + currentY + ")");

                    // Appliquer l'effet aux joueurs présents sur la case
                    for (Client joueurCible : currentCase.clients) {
                        System.out.println("AttaqueZoneAutourJoueur: Joueur détecté : " + joueurCible.getPseudo());

                        for (var effet : competence.getEffets()) {
                            ApplicationEffet applicationEffet = new ApplicationEffet(
                                    effet, joueurCible, personnage.getEntite().getPointAttaque());
                            GestionEffets.ajouterEffet(joueurCible, applicationEffet);
                            System.out.println("AttaqueZoneAutourJoueur: Joueur " + joueurCible.getPseudo() + " subit des dégâts : " + personnage.getEntite().getPointAttaque());
                        }
                    }

                    // Appliquer l'effet aux monstres présents sur la case
                    for (Monstre monstreCible : currentCase.monstres) {
                        System.out.println("AttaqueZoneAutourJoueur: monstre détecté ");

                        for (var effet : competence.getEffets()) {
                            ApplicationEffetMonstre applicationEffetMonstre = new ApplicationEffetMonstre(
                                    effet, monstreCible, personnage.getEntite().getPointAttaque());
                            GestionEffets.ajouterEffet(monstreCible, applicationEffetMonstre);
                            System.out.println("AttaqueZoneAutourJoueur: Monstre subit des dégâts : " + personnage.getEntite().getPointAttaque());
                        }
                    }
                }
            }

            // Pause avant la prochaine application
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("AttaqueZoneAutourJoueur: Interruption du thread.");
                break;
            }
        }

        System.out.println("AttaqueZoneAutourJoueur: Fin de l'attaque.");
        GestionCompetence.RetirerCompetence(competence);
    }
}

