package serveur.serveurjeux.Entity;

import java.util.ArrayList;
import java.util.List;

public class MapChunk {
    public static List<List<Chunk>> mapChunks = new ArrayList<>();
    public static int tailleChunks = 32;

    public static void creerMapChunk(){
        int taille = MapCase.cases.size()/tailleChunks;
        for (int y = 0; y < taille; y++) {
            mapChunks.add(new ArrayList<>());
            for (int x = 0; x < taille; x++) {
                mapChunks.get(y).add(new Chunk(x*32,y*32,x*32+32,y*32+32));
            }
        }
    }
}

