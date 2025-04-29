package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class DemandeLogin implements Serializable, Message {
    public static final int TYPE = 4;

    public String pseudo;
    public String mdp;

    @Override
    public int getType() {
        return TYPE;
    }
}
