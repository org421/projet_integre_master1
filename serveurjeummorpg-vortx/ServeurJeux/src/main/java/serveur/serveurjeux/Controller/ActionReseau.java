package serveur.serveurjeux.Controller;

import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.Reception;
import serveur.serveurjeux.DTO.typeMessage.*;
import serveur.serveurjeux.DTO.typeReponse.*;
import serveur.serveurjeux.Entity.*;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.Entity.Competence.Competence;
import serveur.serveurjeux.Entity.Effet.ApplicationEffet;
import serveur.serveurjeux.Entity.Effet.Effet;
import serveur.serveurjeux.Entity.Inventaire.Inventaire;
import serveur.serveurjeux.Entity.Item.Consommable.Potion;
import serveur.serveurjeux.Entity.Item.Objet;
import serveur.serveurjeux.Entity.NPC.Monstre;
import serveur.serveurjeux.Mapper.InventaireMapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.UUID;

public class ActionReseau {


    //Ecriture et envoie de l'UUID au client
    public static void EnvoieUUID(Client client) throws IOException {
        client.setUuid(UUID.randomUUID().toString());
        Reception reception = new Reception();
        ReponseUUID reponseUUID = new ReponseUUID(client.getUuid());
        reception.ajouterMessage(reponseUUID);
        client.writer.writeObject(reception);
        client.writer.flush();
        client.writer.reset();
    }

    //Identifaction de siteWeb
    public static void IdentificationSiteWeb(Client client) throws IOException {
        SiteWeb.client = client;
    }

    //Envoie de login au siteweb
    public static void DemandeLogin(Client client, DemandeLogin demande) throws IOException {
        int index = Clients.clients.indexOf(client);
        Reception reception = new Reception();
        ReponseDemandeLoginSiteWeb reponseDemandeLoginSiteWeb = new ReponseDemandeLoginSiteWeb();
        reponseDemandeLoginSiteWeb.pseudo = demande.pseudo;
        reponseDemandeLoginSiteWeb.mdp = demande.mdp;
        reponseDemandeLoginSiteWeb.index = index;
        reception.ajouterMessage(reponseDemandeLoginSiteWeb);

        SiteWeb.client.writer.writeObject(reception);
        SiteWeb.client.writer.flush();
        SiteWeb.client.writer.reset();
    }

    //receptionEtEnvoie du login au client
    public static void DemandeLoginSiteWeb(Client client, DemandeLoginSiteWeb demande) throws IOException {
        int index = demande.index;
        client = Clients.clients.get(index);
        Reception reception = new Reception();

        if(demande.inventaireDTO != null){
            InventaireMapper inventaireMapper = new InventaireMapper();
            client.inventaire = inventaireMapper.inventaireDTOtoInventaire(demande.inventaireDTO);
            ReponseInventaire reponseInventaire = new ReponseInventaire();
            reponseInventaire.inventaire = inventaireMapper.inventaireToInventaireDTOClient(client.inventaire);
        } else{
            System.out.println("inventaire null !");
        }

        if(demande.personnage != null) {
            client.personnage = demande.personnage;
            ReponseInformationPersonnage reponseInformationPersonnage = new ReponseInformationPersonnage();
            reponseInformationPersonnage.personnage = demande.personnage;
            reception.ajouterMessage(reponseInformationPersonnage);
            MapCase.cases.get(client.personnage.getEntite().getyMatrice()).get(client.personnage.getEntite().getxMatrice()).clients.add(client);
            MapChunk.mapChunks.get(client.personnage.getEntite().getyMatrice()/MapChunk.tailleChunks).get(client.personnage.getEntite().getxMatrice()/MapChunk.tailleChunks).clients.add(client);
        }
        if(demande.valide){
            client.pseudo = demande.pseudo;
        }
        ReponseDemandeLogin reponseDemandeLogin = new ReponseDemandeLogin();
        reponseDemandeLogin.valide = demande.valide;
        reponseDemandeLogin.pseudo = demande.pseudo;
        reception.ajouterMessage(reponseDemandeLogin);
        client.writer.writeObject(reception);
        client.writer.flush();
        client.writer.reset();
    }

    public static void CreationPersonnage(Client client, DemandeCreationPersonnage demande, Envoie envoie) throws IOException {
        Personnage personnage = new Personnage(demande.classPersonnage);
        client.personnage = personnage;
        client.inventaire = new Inventaire();
        Reception reception = new Reception();
        ReponseCreationPersonnage reponseCreationPersonnage = new ReponseCreationPersonnage();
        reponseCreationPersonnage.personnage = personnage;
        reponseCreationPersonnage.pseudo = envoie.pseudo;
        reception.ajouterMessage(reponseCreationPersonnage);
        SiteWeb.client.writer.writeObject(reception);
        SiteWeb.client.writer.flush();
        SiteWeb.client.writer.reset();
        client.writer.writeObject(reception);
        client.writer.flush();
        client.writer.reset();
        MapChunk.mapChunks.get(client.personnage.getEntite().getyMatrice()/MapChunk.tailleChunks).get(client.personnage.getEntite().getxMatrice()/MapChunk.tailleChunks).clients.add(client);
        ActionReseau.SauvegarderToutLesClients();
    }


    public static void DemandeDeplacement(Client client, DemandeDeplacement demandeDeplacement) throws IOException {
        Deplacement.deplacerPersonnage(client,demandeDeplacement.direction);
//        Deplacement.envoieInformationJoueurProche(client);
//        Deplacement.envoieInformationDeplacementJoueurProche(client, demandeDeplacement.direction);
    }

    public static void DemandeInfoNbJoueur(Client client) throws IOException {
        Reception reception = new Reception();
        ReponseNbJoueurs reponseNbJoueurs = new ReponseNbJoueurs(InfoServeur.getNbJoueur());
        reception.ajouterMessage(reponseNbJoueurs);
        client.writer.writeObject(reception);
        client.writer.flush();
        client.writer.reset();
    }

    public static void SauvegarderToutLesClients() throws IOException {
        Reception reception = new Reception();
        for(Client client : Clients.clients){
            if(client.pseudo != null && client.personnage != null){
                ReponseSauvegardePersonnage reponseSauvegardePersonnage = new ReponseSauvegardePersonnage();
                reponseSauvegardePersonnage.pseudo = client.pseudo;
                reponseSauvegardePersonnage.personnage = client.personnage;
                reception.ajouterMessage(reponseSauvegardePersonnage);
            }
        }
        SiteWeb.client.writer.writeObject(reception);
        SiteWeb.client.writer.flush();
        SiteWeb.client.writer.reset();
    }

    public static void ChangementCaseInventaire(DemandeChangementCaseInventaire demande, Client client) throws IOException {
        Objet objetDepart = null;
        Competence competenceDepart = null;
        Objet objetArrive = null;
        Competence competenceArrive = null;

        if(client.inventaire != null){
            //récupération de la première case
            if(!client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].isVide()){
                if(client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].getObjet() != null){
                    objetDepart = client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].getObjet();
                } else if(client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].getCompetence() != null){
                    competenceDepart = client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].getCompetence();
                }
            }

            //récupération de la seconde case
            if(!client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].isVide()){
                if(client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].getObjet() != null){
                    objetArrive = client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].getObjet();
                } else if(client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].getCompetence() != null){
                    competenceArrive = client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].getCompetence();
                }
            }

            //changement entre les cases
            if(objetDepart != null){
                client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].ajouterObjet(objetDepart);
            } else if(competenceDepart != null){
                client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].ajouterCompetence(competenceDepart);
            } else {
                client.inventaire.inventaire[demande.indexYArrivee][demande.indexXArrivee].ajouterObjet(null);
            }

            if(objetArrive != null){
                client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].ajouterObjet(objetArrive);
            } else if(competenceArrive != null){
                client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].ajouterCompetence(competenceArrive);
            } else {
                client.inventaire.inventaire[demande.indexYDepart][demande.indexXDepart].ajouterObjet(null);
            }
        }
    }

    public static void utilisationCase(DemandeCaseSelection demande, Client client) throws IOException {
        if(client.inventaire != null && client.inventaire.inventaire[demande.indexY][demande.indexX] != null ){
            if(!client.inventaire.inventaire[demande.indexY][demande.indexX].isVide()){
                if(client.inventaire.inventaire[demande.indexY][demande.indexX].getObjet() != null){ //cas pour un objet
                    switch (client.inventaire.inventaire[demande.indexY][demande.indexX].getObjet().getType()){
                        case 6: //potion
                            System.out.println("Il active une potion");
                            Potion potion = new Potion(client.inventaire.inventaire[demande.indexY][demande.indexX].getObjet().getId());
                            for(Effet effet : potion.effets){
                                System.out.println(effet.getNom());
                                ApplicationEffet applicationEffet = new ApplicationEffet(effet, client);
                                GestionEffets.ajouterEffet(client,applicationEffet);
                            }
                            break;
                        default:
                            break;
                    }
                } else if (client.inventaire.inventaire[demande.indexY][demande.indexX].getCompetence() != null){ //cas pour une compétance
                    Competence competence = new Competence(client.inventaire.inventaire[demande.indexY][demande.indexX].getCompetence().getId());
                    competence.direction = demande.direction;
                    competence.client = client;
                    try {
                        competence.paterne.start();
                        GestionCompetence.AjouterCompetence(competence);
                    } catch (Exception e){
                        System.out.println("Erreur: Mauvaise démarrage de compétance. plus info: "+ e.getMessage());
                    }
                }
            }
        }
    }
}
