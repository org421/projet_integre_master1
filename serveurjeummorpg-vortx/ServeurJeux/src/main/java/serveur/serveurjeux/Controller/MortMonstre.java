package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.MapCase;
import serveur.serveurjeux.Entity.MapChunk;
import serveur.serveurjeux.Entity.NPC.Monstre;

public class MortMonstre {

    public static void gererMortMonstre(Monstre monstre) {
        int chunkX = monstre.getXMatrice() / MapChunk.tailleChunks;
        int chunkY = monstre.getYMatrice() / MapChunk.tailleChunks;
        GestionEffets.supprimerToutLesEffetDunMonstre(monstre);
        monstre.setX(0.16F);
        monstre.setY(0.30F);
        monstre.setXMatrice(0);
        monstre.setYMatrice(0);

        // on arrête la politique du monstre
        monstre.arreterPolitiqueAttaque();

        if (MapChunk.mapChunks.get(chunkY).get(chunkX).monstres.contains(monstre)) {
            MapChunk.mapChunks.get(chunkY).get(chunkX).monstres.remove(monstre);
        }
        // on ne fait pas réapparaitre le monstre
//        MapChunk.mapChunks.get(0).get(0).monstres.add(monstre);

        if (MapCase.cases.get(chunkY).get(chunkX).monstres.contains(monstre)) {
            MapCase.cases.get(chunkY).get(chunkX).monstres.remove(monstre);
        }
        // on ne fait pas réapparaitre le monstre
//        MapCase.cases.get(0).get(0).monstres.add(monstre);

        // on ne redonne pas la vie au monstre, puisqu'il ne réapparait pas
//        monstre.setPv(monstre.getPvMax());
    }

}
