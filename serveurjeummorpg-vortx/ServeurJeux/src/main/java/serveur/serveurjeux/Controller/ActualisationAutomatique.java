package serveur.serveurjeux.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import serveur.serveurjeux.DTO.Entity.Sort;
import serveur.serveurjeux.DTO.Reception;
import serveur.serveurjeux.DTO.typeReponse.*;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.Clients;
import serveur.serveurjeux.Entity.Competence.Competence;
import serveur.serveurjeux.Entity.MapCase;
import serveur.serveurjeux.Entity.SiteWeb;
import serveur.serveurjeux.Mapper.InventaireMapper;

import java.io.IOException;

public class ActualisationAutomatique implements Runnable {
    public Client client;

//    @Override
//    public void run() {
//        InventaireMapper inventaireMapper = new InventaireMapper();
//        while (true) {
//            try {
//                Thread.sleep(20);
//                for(Client client : Clients.clients){
//                    if(client.personnage != null && client.inventaire != null){
//                        Reception reception = new Reception(); //creation message d'envoie
//
//                        ReponseNettoyageAffichage reponseNettoyageAffichage = new ReponseNettoyageAffichage(); //nettoyage de l'affichage
//                        reception.ajouterMessage(reponseNettoyageAffichage);
//
//                        ReponseInformationPersonnage reponseInformationPersonnage = new ReponseInformationPersonnage();
//                        reponseInformationPersonnage.personnage = client.personnage;
//                        reception.ajouterMessage(reponseInformationPersonnage);
//
//
//                        ReponseInventaire reponseInventaire = new ReponseInventaire(); //information inventaire
//                        reponseInventaire.inventaire = inventaireMapper.inventaireToInventaireDTOClient(client.inventaire);
//                        reception.ajouterMessage(reponseInventaire);
//
//                        for (Competence competence : GestionCompetence.getCompetences()) {//envoie des informations de compétence
//                            Sort sort = new Sort();
//                            sort.setX(competence.positionX);
//                            sort.setY(competence.positionY);
//                            sort.setId_competence(competence.getId());
//                            ReponseAffichageSort affichageSort = new ReponseAffichageSort();
//                            affichageSort.sort = sort;
//                            reception.ajouterMessage(affichageSort);
//                        }
//
//                        client.writer.writeObject(reception); //envoie de ce packet
//                        client.writer.flush();
//                        client.writer.reset();
//
//                        Deplacement.envoieInformationJoueurProche(client); //packet affichage des joueur proche
//
//                    }
//                }
//
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    @Override
    public void run() {
        InventaireMapper inventaireMapper = new InventaireMapper();
        while (true) {
            if(SiteWeb.client == client){
                break;
            }
            try {
                Thread.sleep(20);
                if(client.personnage != null && client.inventaire != null){
                    Reception reception = new Reception(); //creation message d'envoie

                    ReponseNettoyageAffichage reponseNettoyageAffichage = new ReponseNettoyageAffichage(); //nettoyage de l'affichage
                    reception.ajouterMessage(reponseNettoyageAffichage);

                    ReponseInformationPersonnage reponseInformationPersonnage = new ReponseInformationPersonnage();
                    reponseInformationPersonnage.personnage = client.personnage;
                    reception.ajouterMessage(reponseInformationPersonnage);


                    ReponseInventaire reponseInventaire = new ReponseInventaire(); //information inventaire
                    reponseInventaire.inventaire = inventaireMapper.inventaireToInventaireDTOClient(client.inventaire);
                    reception.ajouterMessage(reponseInventaire);

                    for (Competence competence : GestionCompetence.getCompetences()) {//envoie des informations de compétence
                        Sort sort = new Sort();
                        sort.setX(competence.positionX);
                        sort.setY(competence.positionY);
                        sort.setId_competence(competence.getId());
                        ReponseAffichageSort affichageSort = new ReponseAffichageSort();
                        affichageSort.sort = sort;
                        reception.ajouterMessage(affichageSort);
                    }

                    client.writer.writeObject(reception); //envoie de ce packet
                    client.writer.flush();
                    client.writer.reset();

                    Deplacement.envoieInformationJoueurProche(client); //packet affichage des joueurs proches
                    Deplacement.envoieInformationMonstreProche(client); //packet affichage des monstres proches

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
