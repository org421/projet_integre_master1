package serveur.serveurjeux.Entity;

public class InfoServeur {
    private static int nbJoueur = 0;

    public static synchronized void addNbJoueur() {
        nbJoueur++;
    }

    public static synchronized int getNbJoueur() {
        return nbJoueur;
    }

    public static synchronized void removeNbJoueur() {
        nbJoueur--;
    }
}
