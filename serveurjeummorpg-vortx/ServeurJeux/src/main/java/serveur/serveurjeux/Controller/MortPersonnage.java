package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.MapCase;
import serveur.serveurjeux.Entity.MapChunk;

public class MortPersonnage {

    public static void gererMortPersonnage(Client client) {
        int chunkX = client.personnage.getEntite().getxMatrice() / MapChunk.tailleChunks;
        int chunkY = client.personnage.getEntite().getyMatrice() / MapChunk.tailleChunks;
        GestionEffets.supprimerToutLesEffetDunJoueur(client);
        client.personnage.getEntite().setX(0.16F);
        client.personnage.getEntite().setY(0.30F);
        client.personnage.getEntite().setxMatrice(0);
        client.personnage.getEntite().setyMatrice(0);

        if (MapChunk.mapChunks.get(chunkY).get(chunkX).clients.contains(client)) {
            MapChunk.mapChunks.get(chunkY).get(chunkX).clients.remove(client);
        }
        MapChunk.mapChunks.get(0).get(0).clients.add(client);

        if (MapCase.cases.get(chunkY).get(chunkX).clients.contains(client)) {
            MapCase.cases.get(chunkY).get(chunkX).clients.remove(client);
        }
        MapCase.cases.get(0).get(0).clients.add(client);

        client.personnage.getEntite().setPv(client.personnage.getEntite().getPvMax());
    }

}
