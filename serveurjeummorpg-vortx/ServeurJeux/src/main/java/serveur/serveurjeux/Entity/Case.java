package serveur.serveurjeux.Entity;

import serveur.serveurjeux.Entity.NPC.Monstre;

import java.util.ArrayList;
import java.util.List;

public class Case {
    public int x;
    public int y;
    public char symbole;
    public List<Client> clients;
    public List<Monstre> monstres;

    public Case(int x, int y, char symbole) {
        this.x = x;
        this.y = y;
        this.symbole = symbole;
        this.clients = new ArrayList<>();
        this.monstres = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "[" + symbole + "]";
    }
}
