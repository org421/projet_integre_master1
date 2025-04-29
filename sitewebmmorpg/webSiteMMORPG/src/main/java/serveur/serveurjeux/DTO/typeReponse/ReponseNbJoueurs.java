package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseNbJoueurs implements Serializable, Message {
    public static final int TYPE = 6;
    private int nbJoueurs;

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public ReponseNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public int getType() {
        return TYPE;
    }
}
