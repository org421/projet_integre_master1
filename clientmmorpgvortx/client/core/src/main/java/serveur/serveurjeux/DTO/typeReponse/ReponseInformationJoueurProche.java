package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseInformationJoueurProche implements Serializable, Message {
    public static final int TYPE = 10;

    public Personnage personnage;

    @Override
    public int getType() {
        return TYPE;
    }



}
