package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.Chunk;
import serveur.serveurjeux.Entity.MapChunk;
import serveur.serveurjeux.Entity.NPC.Monstre;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class VerifChunk implements Runnable{

    public static List<Chunk> chunksActive = new ArrayList<>(); // Liste des chunks actives (les chunks ou un joueur se trouve à proximité)

    @Override
    public void run() { //Va dire quels chunks sont actifs ou non, en fonction de la position des clients
        while (true) {
            try {
                Thread.sleep(1000); //Verif chaque secondes

                chunksActive.clear(); //on réinitialise la liste des chunks actives

                List<Chunk> listChunk;
                Chunk chunk;
                for(int y = 0; y < MapChunk.mapChunks.size(); y++){ // Pour chaque ligne de chunk
                    listChunk = MapChunk.mapChunks.get(y);
                    for(int x = 0; x < listChunk.size(); x++){ // Pour chaque chunk de la ligne
                        chunk = listChunk.get(x);
                        if(!chunk.clients.isEmpty()){ // Si un ou plusieurs clients se trouve sur le chunk
//                            System.out.println("Joueur(s) dans le chunk ("+chunk.xStart/MapChunk.tailleChunks+","+chunk.yStart/MapChunk.tailleChunks+")");
//                            System.out.println("chunk"+chunk.xStart+","+chunk.yStart+" actif -> monstres : "+chunk.monstres.size()+", clients : "+chunk.clients.size());
                            ajoutChunk(x, y);
                            ajoutVoisinsChunk(x, y);
                        }
                    }
                }

                //verif des chunk inactif aprés premier passage, car sinon certain serait passé en inactif alors qu'ils seraient actifs plus tard
                for(int y = 0; y < MapChunk.mapChunks.size(); y++){ // Pour chaque ligne de chunk
                    listChunk = MapChunk.mapChunks.get(y);
                    for(int x = 0; x < listChunk.size(); x++){ // Pour chaque chunk de la ligne
                        chunk = listChunk.get(x);
                        if(!chunksActive.contains(chunk)){// Le chunk n'est pas actif, on vire tous les monstres
//                            System.out.println("chunk"+chunk.xStart+","+chunk.yStart+" inactif -> monstres : "+chunk.monstres.size()+", clients : "+chunk.clients.size());
                            arretMonstres(chunk);
                            chunk.monstres.clear();
                        }
                    }
                }

                if(chunksActive.isEmpty()){
//                    System.out.println("Aucun chunk est active");
                } else {
//                    System.out.println(chunksActive.size() + " chunks activés :");
                    for(int  i = 0; i < chunksActive.size(); i++){
//                        System.out.println("chunk : (x:" + chunksActive.get(i).xStart +  ",y:" + chunksActive.get(i).yStart + ") -> ( x:"+ chunksActive.get(i).xEnd +   ",y:"+ chunksActive.get(i).yEnd + ") | ("+chunksActive.get(i).xStart/32+","+chunksActive.get(i).yStart/32+")");
                    }
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Ajoute le chunk(x, y) dans la liste des chunks activés s'il n'y est pas
    public void ajoutChunk(int x, int y){
        if(!chunksActive.contains(MapChunk.mapChunks.get(y).get(x))){
            chunksActive.add(MapChunk.mapChunks.get(y).get(x));
        }
    }
    // Ajoute les huit voisins du chunk(x, y), s'ils existent, dans la liste des chunks activés s'ils n'y sont pas
    public void ajoutVoisinsChunk(int x, int y){
        if(x != 0) {
            ajoutChunk(x-1, y);
        }
        if(x != 0 && y != 0){
            ajoutChunk(x-1, y-1);
        }
        if(y != 0){
            ajoutChunk(x, y-1);
        }
        if(y != 0 && x != MapChunk.mapChunks.get(y-1).size() - 1){
            ajoutChunk(x+1, y-1);
        }
        if(x != MapChunk.mapChunks.get(y).size() - 1){
            ajoutChunk(x+1, y);
        }
        if(y != MapChunk.mapChunks.size() - 1 && x != MapChunk.mapChunks.get(y+1).size() - 1){
            ajoutChunk(x+1, y+1);
        }
        if(y != MapChunk.mapChunks.size() - 1){
            ajoutChunk(x, y+1);
        }
        if(y != MapChunk.mapChunks.size() - 1 && x != 0){
            ajoutChunk(x-1, y+1);
        }
    }

    //Arret tous les threads des monstres
    public void arretMonstres(Chunk chunk) {
//        System.out.println("chunk"+chunk.xStart+","+chunk.yStart+" -> monstres : "+chunk.monstres.size());
        if (!chunk.monstres.isEmpty()) { // on arrete les monstre s'il y en a
            System.out.println("Il y a encore des monstres dans ce chunk"+chunk.xStart+","+chunk.yStart+" inactif");
            for (Monstre monstre : chunk.monstres) {
                monstre.arreterPolitiqueAttaque();
                System.out.println("Monstre arrêter au chunk: " + chunk.xStart + "," + chunk.yStart);
            }
            chunk.monstres.clear(); //on clear la liste, maintenant vidé, de monstre
        }
    }
}
