package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.Chunk;
import serveur.serveurjeux.Entity.NPC.Monstre;
import serveur.serveurjeux.Entity.NPC.Monstres.Loup;
import serveur.serveurjeux.Entity.Politiques.Attaque.Agressif;

import java.util.ArrayList;
import java.util.List;

public class GestionChunk implements Runnable{

    @Override
    public void run() { //Va dire quels chunks sont actifs ou non, en fonction de la position des clients
        while (true) {
            try {
                Thread.sleep(1000); //Verif chaque secondes
                List<Chunk> chunks = new ArrayList<>(VerifChunk.chunksActive);
                for(Chunk chunk : chunks){
                    //A changé
//                    if(chunk.monstres.isEmpty() && chunk.clients.isEmpty()){ //Si le chunk DU JOUEUR n'a pas de monstre, on en fait apparaitre un
                    if(chunk.monstres.isEmpty()){ //Si le chunk DU JOUEUR n'a pas de monstre, on en fait apparaitre un
                        Loup loup = new Loup(new Agressif(), chunk);
                        loup.lancement();
//                        chunk.monstres.add(loup); //monstre déjà ajouté grâce à son constructeur
                        System.out.println("Monstre ajouté en : " + chunk.xStart/32 + "," + chunk.yStart/32 + " : " + loup.getX() + "," + loup.getY());
                    }
                    //*******************************************
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
