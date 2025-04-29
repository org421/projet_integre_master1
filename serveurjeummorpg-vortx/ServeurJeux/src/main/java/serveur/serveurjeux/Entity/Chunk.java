package serveur.serveurjeux.Entity;

import serveur.serveurjeux.Entity.NPC.Monstre;

import java.util.ArrayList;
import java.util.List;

public class Chunk{
    public int xStart;
    public int yStart;
    public int xEnd;
    public int yEnd;
    public List<Client> clients;
    public List<Monstre> monstres; //La liste des monstres présents dans le chunk

    public Chunk(int xStart, int yStart, int xEnd , int yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.clients = new ArrayList<>();
        this.monstres = new ArrayList<>();
    }
}
