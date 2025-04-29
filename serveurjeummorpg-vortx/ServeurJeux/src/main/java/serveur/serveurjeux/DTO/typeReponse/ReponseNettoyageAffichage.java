package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseNettoyageAffichage implements Serializable, Message {
    public static final int TYPE = 11;


    @Override
    public int getType() {
        return TYPE;
    }
}
