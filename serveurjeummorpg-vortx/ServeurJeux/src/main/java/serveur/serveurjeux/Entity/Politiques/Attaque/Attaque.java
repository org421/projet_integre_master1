package serveur.serveurjeux.Entity.Politiques.Attaque;

import serveur.serveurjeux.Entity.NPC.Monstre;

public interface Attaque extends Runnable {
    int getType();
    Monstre getMonstre();
    void setMonstre(Monstre monstre);
    void run();
    void stopping(Thread thread);
}
