package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseDemandeLogin implements Serializable, Message {
    public static final int TYPE = 3;
    public boolean valide;
    public String pseudo;

    @Override
    public int getType() {
        return TYPE;
    }
}
