package serveur.serveurjeux.Entity.Competence;

import serveur.serveurjeux.Controller.GestionCompetence;
import serveur.serveurjeux.Controller.GestionEffets;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.Entity.Case;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.Effet.ApplicationEffet;
import serveur.serveurjeux.Entity.Effet.ApplicationEffetMonstre;
import serveur.serveurjeux.Entity.Effet.Effet;
import serveur.serveurjeux.Entity.MapCase;
import serveur.serveurjeux.Entity.NPC.Monstre;

import java.util.ArrayList;
import java.util.List;

public class PetiteAttaqueDevantJoueur implements Runnable {
    private final Competence competence;

    public PetiteAttaqueDevantJoueur(Competence competence) {
        this.competence = competence;
    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        Case caseSelectionner = null;
//        Personnage personnage = competence.client.personnage;
        Personnage personnage;
        Monstre monstre;
        int caseX;
        int caseY;
        int attaque;

        if(competence.client != null){ //compétance lancé par un client
            personnage = competence.client.personnage;
            caseX = personnage.getEntite().getxMatrice();
            caseY = personnage.getEntite().getyMatrice();
            attaque = personnage.getEntite().getPointAttaque();
        } else { //compétence lancé par un monstre
            monstre = competence.monstre;
            caseX = monstre.getXMatrice();
            caseY = monstre.getYMatrice();
            attaque = monstre.getPointAttaque();
        }

        switch (competence.direction) {
            case 0:
//                if(personnage.getEntite().getyMatrice()-1 > -1){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()-1).get(personnage.getEntite().getxMatrice());
//                    competence.positionX = personnage.getEntite().getxMatrice();
//                    competence.positionY = personnage.getEntite().getyMatrice()-1;
//                }
                if(caseY-1 > -1){
                    caseSelectionner = MapCase.cases.get(caseY-1).get(caseX);
                    competence.positionX = caseX;
                    competence.positionY = caseY-1;
                }
                break;
            case 1:
//                if(personnage.getEntite().getyMatrice()-1 > -1 && personnage.getEntite().getxMatrice() + 1 < MapCase.cases.size() ){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()-1).get(personnage.getEntite().getxMatrice()+1);
//                    competence.positionX = personnage.getEntite().getxMatrice()+1;
//                    competence.positionY = personnage.getEntite().getyMatrice()-1;
//                }
                if(caseY-1 > -1 && caseX + 1 < MapCase.cases.size() ){
                    caseSelectionner = MapCase.cases.get(caseY-1).get(caseX+1);
                    competence.positionX = caseX+1;
                    competence.positionY = caseY-1;
                }
                break;
            case 2:
//                if(personnage.getEntite().getxMatrice() + 1 < MapCase.cases.size() ){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()).get(personnage.getEntite().getxMatrice()+1);
//                    competence.positionX = personnage.getEntite().getxMatrice()+1;
//                    competence.positionY = personnage.getEntite().getyMatrice();
//                }
                if(caseX + 1 < MapCase.cases.size() ){
                    caseSelectionner = MapCase.cases.get(caseY).get(caseX+1);
                    competence.positionX = caseX+1;
                    competence.positionY = caseY;
                }
                break;
            case 3:
//                if(personnage.getEntite().getyMatrice()+1 < MapCase.cases.size() && personnage.getEntite().getxMatrice() + 1 < MapCase.cases.size() ){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()+1).get(personnage.getEntite().getxMatrice()+1);
//                    competence.positionX = personnage.getEntite().getxMatrice()+1;
//                    competence.positionY = personnage.getEntite().getyMatrice()+1;
//                }
                if(caseY+1 < MapCase.cases.size() && caseX + 1 < MapCase.cases.size() ){
                    caseSelectionner = MapCase.cases.get(caseY+1).get(caseX+1);
                    competence.positionX = caseX+1;
                    competence.positionY = caseY+1;
                }
                break;
            case 4:
//                if(personnage.getEntite().getyMatrice()+1 > MapCase.cases.size()){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()+1).get(personnage.getEntite().getxMatrice());
//                    competence.positionX = personnage.getEntite().getxMatrice();
//                    competence.positionY = personnage.getEntite().getyMatrice()+1;
//                }
                if(caseY+1 > MapCase.cases.size()){
                    caseSelectionner = MapCase.cases.get(caseY+1).get(caseX);
                    competence.positionX = caseX;
                    competence.positionY = caseY+1;
                }
                break;
            case 5:
//                if(personnage.getEntite().getyMatrice()+1 < MapCase.cases.size() && personnage.getEntite().getxMatrice() - 1 > -1 ){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()+1).get(personnage.getEntite().getxMatrice()-1);
//                    competence.positionX = personnage.getEntite().getxMatrice()-1;
//                    competence.positionY = personnage.getEntite().getyMatrice()+1;
//                }
                if(caseY+1 < MapCase.cases.size() && caseX - 1 > -1 ){
                    caseSelectionner = MapCase.cases.get(caseY+1).get(caseX-1);
                    competence.positionX = caseX-1;
                    competence.positionY = caseY+1;
                }
                break;
            case 6:
//                if(personnage.getEntite().getxMatrice() - 1 > -1 ){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()).get(personnage.getEntite().getxMatrice()-1);
//                    competence.positionX = personnage.getEntite().getxMatrice()-1;
//                    competence.positionY = personnage.getEntite().getyMatrice();
//                }
                if(caseX - 1 > -1 ){
                    caseSelectionner = MapCase.cases.get(caseY).get(caseX-1);
                    competence.positionX = caseX-1;
                    competence.positionY = caseY;
                }
                break;
            case 7:
//                if(personnage.getEntite().getyMatrice()-1 > -1 && personnage.getEntite().getxMatrice() - 1 > -1 ){
//                    caseSelectionner = MapCase.cases.get(personnage.getEntite().getyMatrice()-1).get(personnage.getEntite().getxMatrice()-1);
//                    competence.positionX = personnage.getEntite().getxMatrice()-1;
//                    competence.positionY = personnage.getEntite().getyMatrice()-1;
//                }
                if(caseY-1 > -1 && caseX - 1 > -1 ){
                    caseSelectionner = MapCase.cases.get(caseY-1).get(caseX-1);
                    competence.positionX = caseX-1;
                    competence.positionY = caseY-1;
                }
                break;
        }
        while (System.currentTimeMillis() - startTime < competence.getDuree()) {
            System.out.println("temps avant fin: " + (System.currentTimeMillis() - startTime) +"/"+ competence.getDuree() );
            if(caseSelectionner != null){
                System.out.println("Attaque sur la case: \n" +
                        "x=" + caseSelectionner.x + " et y=" + caseSelectionner.y);
                List<Client> joueurCibles = new ArrayList<>(caseSelectionner.clients);
                List<Monstre> monstreCibles = new ArrayList<>(caseSelectionner.monstres);
                // Application des dégats aux joueurs
                for (Client joueurCible : joueurCibles){
                    if(!competence.clientsToucher.contains(joueurCible)) {
                        competence.clientsToucher.add(joueurCible);
                        for (Effet effet : competence.getEffets()) {
//                            ApplicationEffet applicationEffet = new ApplicationEffet(effet, joueurCible, personnage.getEntite().getPointAttaque());
                            ApplicationEffet applicationEffet = new ApplicationEffet(effet, joueurCible, attaque);
                            GestionEffets.ajouterEffet(joueurCible, applicationEffet);
                        }
                    }
                }

                // Application des dégats aux monstres
                for (Monstre monstreCible : monstreCibles){
                    if(!competence.monstresToucher.contains(monstreCible)) {
                        System.out.println("Un monstre est touché");
                        competence.monstresToucher.add(monstreCible);
                        for (Effet effet : competence.getEffets()) {
//                            ApplicationEffetMonstre applicationEffetMonstre = new ApplicationEffetMonstre(effet, monstreCible, personnage.getEntite().getPointAttaque());
                            ApplicationEffetMonstre applicationEffetMonstre = new ApplicationEffetMonstre(effet, monstreCible, attaque);
                            if(competence.monstre == null) { //Si ce n'est pas un monstre qui envoie cette compétance
                                GestionEffets.ajouterEffet(monstreCible, applicationEffetMonstre);
                            }
                        }
                    }
                }

            }
            try {
                Thread.sleep(1000); // Pause entre chaque tick d'exécution
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("Tentative de retrait de compétance...");
        GestionCompetence.RetirerCompetence(competence);
        System.out.println("Retrait de la compétance...");
    }
}
