package serveur.serveurjeux.Entity.Politiques.Attaque;

import serveur.serveurjeux.Controller.Deplacement;
import serveur.serveurjeux.Controller.GestionCompetence;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.Competence.Competence;
import serveur.serveurjeux.Entity.MapChunk;
import serveur.serveurjeux.Entity.NPC.Monstre;
import serveur.serveurjeux.Entity.Utility.Pile;
import serveur.serveurjeux.Entity.Utility.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Agressif implements Attaque {
    public static final int TYPE = 1;
    private volatile boolean enCours = true; // volatile, parce que sinon, le thread peut ne pas voir la modif du boolean immédiatement, voir jamais
    private Monstre monstre;
    private final int agroDistance = 10*10; //repère à 10 cases

    @Override
    public int getType() { return  TYPE; }
    @Override
    public Monstre getMonstre() { return monstre; }
    @Override
    public void setMonstre(Monstre monstre) {
//        System.out.println("assignation du monstre");
        if(monstre == null){
//            System.out.println("... mais monstre null");
        }
        this.monstre = monstre;
    }

    @Override
    public void run() {
        int chunkX;
        int chunkY;
        float dx;
        float dy;
        float newDx;
        float newDy;
        List<Client> clientsProches = new ArrayList<>();
        Client cible;
        Position destination;
        Position posCible;
        Competence competence = null;

        while (enCours) {

            if(monstre == null) {
//                System.out.println("monstre null");
            }

            try {
                Thread.sleep(50);
//                System.out.println("Je suis un agressif");

                //calcule du chunk actuel
                chunkX = monstre.getXMatrice()/32;
                chunkY = monstre.getYMatrice()/32;
//                System.out.println("Je suis dans le chunk " + chunkX + " " + chunkY);

                //recherche des clients proches
                clientsProches = rechercheClientsProches(chunkX, chunkY);
//                System.out.println("Il y a " + clientsProches.size() + " dans les chunks proche de moi");

                if(!clientsProches.isEmpty()) {
                    //calcule du client le plus proche
                    cible = clientPlusProche(clientsProches, monstre.getX(), monstre.getY());
                    posCible = new Position( (float)(Math.floor(cible.personnage.getEntite().getX())+0.5) , (float)(Math.floor(cible.personnage.getEntite().getY())+0.5));

//                    if( (int)Math.floor(posCible.getX()) < this.monstre.getX() && this.monstre.getX() < (int)Math.ceil(posCible.getX())  &&  (int)Math.floor(posCible.getY()) < this.monstre.getY() && this.monstre.getY() < (int)Math.ceil(posCible.getY()) ){
                    if( caseVoisine(new Position((int)Math.floor(posCible.getX()), (int)Math.floor(posCible.getY())), new Position(this.monstre.getXMatrice(), this.monstre.getYMatrice())) ){
                        int direction = direction(new Position(this.monstre.getXMatrice(), this.monstre.getYMatrice()), new Position((int)Math.floor(posCible.getX()), (int)Math.floor(posCible.getY())));
//                        System.out.println("Le monstre attaque en "+ direction + " !!!");

//                        competence = this.monstre.getCompetence().get(0);
                        if (competence == null || competence.paterne == null || competence.paterne.getState() == Thread.State.TERMINATED) {
                            System.out.println("Le monstre lance une nouvelle compétence !!!");
                            competence = new Competence(1);
                            competence.direction = direction;
                            competence.monstre = monstre;

                            try {
                                competence.paterne.start();
                                GestionCompetence.AjouterCompetence(competence);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Erreur: Mauvais démarrage de compétence. Plus d'info : " + e.getMessage()); // LA
                            }
                        } else {
                            System.out.println("Le monstre attend que la compétence actuelle se termine.");
                        }

                    } else {
                        if (this.monstre.getCible() == null || !this.monstre.getCible().equals(posCible)) { //On recherche un nouveau chemin si la cible du monstre a changée
                            this.monstre.setCible(posCible);
//                        System.out.println("nouvelle position cible : " + monstre.getCible());

                            //Recherche d'un chemin
                            monstre.setChemin(Deplacement.rechercheChemin(monstre.getX(), monstre.getY(), monstre.getCible().getX(), monstre.getCible().getY()));

                        }
                        if (monstre.getChemin() != null && !monstre.getChemin().isEmpty()) { // le monstre ne se déplace que s'il le peut, sinon, il ne fait rien
                            destination = this.monstre.getChemin().get(0);

                            //Calcule des directions
                            dx = destination.getX() - monstre.getX();
                            dy = destination.getY() - monstre.getY();

//                        System.out.println("Je suis en "+monstre.getX()+", "+monstre.getY());
//                        System.out.println("Je me déplace à "+destination);
                            Deplacement.deplacerMonstre(monstre, destination.getX(), destination.getY());

                            //Calcule des directions
                            newDx = destination.getX() - monstre.getX();
                            newDy = destination.getY() - monstre.getY();

                            if ((dx >= 0 && newDx < 0) || (dy >= 0 && newDy < 0)) {
                                //Changement d'étape
//                            System.out.println("une étape passer ! ");
//                            System.out.println("avant : ");
//                            for(Position pos :  monstre.getChemin()){
//                                System.out.println(pos.getX()+";"+pos.getY());
//                            }
                                monstre.getChemin().remove(0);
//                            System.out.println("après : ");
//                            for(Position pos :  monstre.getChemin()){
//                                System.out.println(pos.getX()+";"+pos.getY());
//                            }
                            }

//                        System.out.println("chemin : ");
//                        for(Position pos :  monstre.getChemin()){
//                            System.out.println(pos.getX()+";"+pos.getY());
//                        }
                        }
                    }

                }
            } catch (InterruptedException e) {
                System.out.println("Aggression interrompu !");
                break;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void stopping(Thread thread) {
        enCours = false;
        thread.interrupt(); // Réveille le thread s'il dort
        System.out.println("Agression arrêté"); }

    //fonction qui va recherche les clients dans le chunk du monstre et ses voisins
    public List<Client> rechercheClientsProches(int chunkX, int chunkY) {
        List<Client> clientsProches = new ArrayList<>();
        List<Client> listClient = MapChunk.mapChunks.get(chunkY).get(chunkX).clients;
        if(!listClient.isEmpty()){
            for(Client client : listClient){ // On remplie la liste des clients proches avec tous les clients du chunk actuel
                // on ajoute le client s'il assez proche du monstre
                if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                    clientsProches.add(client);
                }
            }
        }

        if(chunkX != 0) { // voisin gauche
            listClient = MapChunk.mapChunks.get(chunkY).get(chunkX-1).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        if(chunkX != 0 && chunkY != 0) { // voisin haut-gauche
            listClient = MapChunk.mapChunks.get(chunkY-1).get(chunkX-1).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        if(chunkY != 0) { // voisin haut
            listClient = MapChunk.mapChunks.get(chunkY-1).get(chunkX).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        if(chunkX != MapChunk.mapChunks.get(0).size()-1 && chunkY != 0) { //voisin haut-droit
            listClient = MapChunk.mapChunks.get(chunkY-1).get(chunkX+1).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        if(chunkX != MapChunk.mapChunks.get(0).size()-1) { //voisin droit
            listClient = MapChunk.mapChunks.get(chunkY).get(chunkX+1).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        if(chunkX != MapChunk.mapChunks.get(0).size()-1 && chunkY != MapChunk.mapChunks.size()-1) { //voisin bas-droit
            listClient = MapChunk.mapChunks.get(chunkY+1).get(chunkX+1).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        if(chunkY != MapChunk.mapChunks.size()-1) { //voisin bas
            listClient = MapChunk.mapChunks.get(chunkY+1).get(chunkX).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        if(chunkX != 0 && chunkY != MapChunk.mapChunks.size()-1) { //voisin bas-gauche
            listClient = MapChunk.mapChunks.get(chunkY+1).get(chunkX-1).clients;
            if(!listClient.isEmpty()){
                for(Client client : listClient){
                    if(distance(this.monstre.getXMatrice(), this.monstre.getYMatrice(), client.personnage.getEntite().getxMatrice(), client.personnage.getEntite().getyMatrice()) <= this.agroDistance){
                        clientsProches.add(client);
                    }
                }
            }
        }
        return clientsProches;
    }

    // Renvoie true si les deux positions en paramètre sont voisins, ou confondue
    public boolean caseVoisine(Position p1, Position p2) {
//        System.out.println("p1 :"+p1+" ; p2 :"+p2);
        if(
                (p1.getX() == p2.getX() && p1.getY() == p2.getY()) ||
                (p1.getX() == p2.getX() && p1.getY() == p2.getY()-1) ||
                (p1.getX() == p2.getX() && p1.getY() == p2.getY()+1) ||
                (p1.getX() == p2.getX()-1 && p1.getY() == p2.getY()) ||
                (p1.getX() == p2.getX()-1 && p1.getY() == p2.getY()-1) ||
                (p1.getX() == p2.getX()-1 && p1.getY() == p2.getY()+1) ||
                (p1.getX() == p2.getX()+1 && p1.getY() == p2.getY()) ||
                (p1.getX() == p2.getX()+1 && p1.getY() == p2.getY()-1) ||
                (p1.getX() == p2.getX()+1 && p1.getY() == p2.getY()+1)
        ){
            return true;
        }
        return false;
    }

    //fonction qui calcule qui est le client le plus proche du monstre
    public Client clientPlusProche(List<Client> clientsProches, float x, float y) {
        Client cible = null;
        for(Client client : clientsProches){
            if(cible == null){
                cible = client;
            } else {
                if(distance(x, y, client.personnage.getEntite().getX(), client.personnage.getEntite().getY()) < distance(x, y, cible.personnage.getEntite().getX(), cible.personnage.getEntite().getY())) {
                    cible = client;
                }
            }
        }
        return cible;
    }

    // calcule la distance euclidienne entre 2 points, sans racine carrée pour aller plus vite, vu qu'on tjr comparer des distances entre eux, et jamais leur valeur pur
    public float distance(float x1, float y1, float x2, float y2) {
        return (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1);
    }

    //Renvoie la direction de la position d'arrivé par rapport à la position de départ
//    0 haut
//    1 haut-droit
//    2 droit
//    3 bas droit
//    4 bas
//    5 bas gauche
//    6 gauche
//    7 haut gauche
    public int direction(Position p1, Position p2) {
        float dx = p2.getX() - p1.getX();
        float dy = p2.getY() - p1.getY();

        if(dx == 0 && dy == 0){
            return 0;
        } else if (dx == 0 && dy < 0) {
            return 0;
        }else if (dx > 0 && dy < 0) {
            return 1;
        }else if (dx > 0 && dy == 0) {
            return 2;
        }else if (dx > 0 && dy > 0) {
            return 3;
        }else if (dx == 0 && dy > 0) {
            return 4;
        }else if (dx < 0 && dy > 0) {
            return 5;
        }else if (dx < 0 && dy == 0) {
            return 6;
        }else if (dx < 0 && dy < 0) {
            return 7;
        } else {
            return 0; //Par défaut
        }
    }

}
