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

public class AttaqueDistanceInstantanee implements Runnable {

    private final Competence competence;

    public AttaqueDistanceInstantanee(Competence competence) {
        this.competence = competence;
    }

    @Override
    public void run() {
        Personnage personnage = competence.client.personnage;
        int startX = personnage.getEntite().getxMatrice();
        int startY = personnage.getEntite().getyMatrice();
        int rayon = competence.getRayon();

        int dx = 0;
        int dy = 0;

        // Print de débogage pour la position et la direction
        System.out.println("AttaqueDistanceInstantanee: Position de départ (" + startX + ", " + startY + ")");
        System.out.println("AttaqueDistanceInstantanee: Direction (" + competence.direction + ")");

        switch (competence.direction) {
            case 0:  // Haut
                dy = -1;
                break;
            case 1:  // Haut-Droite
                dx = 1; dy = -1;
                break;
            case 2:  // Droite
                dx = 1;
                break;
            case 3:  // Bas-Droite
                dx = 1; dy = 1;
                break;
            case 4:  // Bas
                dy = 1;
                break;
            case 5:  // Bas-Gauche
                dx = -1; dy = 1;
                break;
            case 6:  // Gauche
                dx = -1;
                break;
            case 7:  // Haut-Gauche
                dx = -1; dy = -1;
                break;
            default:
                System.out.println("AttaqueDistanceInstantanee: Direction non supportée (" + competence.direction + ")");
                return;
        }

        // Parcourir les cases sur la trajectoire du laser jusqu'à la portée maximale
        for (int i = 1; i <= rayon; i++) {
            int currentX = startX + dx * i;
            int currentY = startY + dy * i;

            // Vérifier les bornes de la map
            if (currentY < 0 || currentY >= MapCase.cases.size()) {
                System.out.println("AttaqueDistanceInstantanee: Hors limites (Y) à i=" + i);
                break;
            }
            if (currentX < 0 || currentX >= MapCase.cases.get(currentY).size()) {
                System.out.println("AttaqueDistanceInstantanee: Hors limites (X) à i=" + i);
                break;
            }

            // Récupérer la case courante et mettre à jour la position du laser
            Case currentCase = MapCase.cases.get(currentY).get(currentX);
            competence.positionX = currentX;
            competence.positionY = currentY;

            System.out.println("AttaqueDistanceInstantanee: Laser atteint case (" + currentX + ", " + currentY + ")");

            // Appliquer l'effet à chaque client présent sur la case (en évitant les répétitions)
            for (Client joueurCible : currentCase.clients) {
                if (!competence.clientsToucher.contains(joueurCible)) {
                    competence.clientsToucher.add(joueurCible);
                    System.out.println("AttaqueDistanceInstantanee: Appliquer effet sur le client " + joueurCible.getPseudo());
                    for (var effet : competence.getEffets()) {
                        ApplicationEffet applicationEffet = new ApplicationEffet(
                                effet, joueurCible, personnage.getEntite().getPointAttaque());
                        GestionEffets.ajouterEffet(joueurCible, applicationEffet);
                        System.out.println("AttaqueDistanceInstantanee: Client " + joueurCible.getPseudo() + " subit effet avec dommages " + personnage.getEntite().getPointAttaque());
                    }
                }
            }

            // Appliquer l'effet à chaque monstre présent sur la case (en évitant les répétitions)
            for (Monstre monstreCible : currentCase.monstres) {
                if (!competence.monstresToucher.contains(monstreCible)) {
                    competence.monstresToucher.add(monstreCible);
                    System.out.println("AttaqueDistanceInstantanee: Appliquer effet sur un monstre");
                    for (var effet : competence.getEffets()) {
                        ApplicationEffetMonstre applicationEffetMonstre = new ApplicationEffetMonstre(
                                effet, monstreCible, personnage.getEntite().getPointAttaque());
                        GestionEffets.ajouterEffet(monstreCible, applicationEffetMonstre);
                        System.out.println("AttaqueDistanceInstantanee: monstre subit effet avec dommages " + personnage.getEntite().getPointAttaque());
                    }
                }
            }

            try {
                Thread.sleep(1000); // Pause entre chaque tick d'exécution
            } catch (InterruptedException e) {
                System.out.println("AttaqueDistanceInstantanee: Thread interrompu");
                return;
            }
        }

        System.out.println("AttaqueDistanceInstantanee: Fin de l'attaque, retrait de la compétence.");
        GestionCompetence.RetirerCompetence(competence);
    }
}