package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;


public class DemandeNbJoueurs implements Serializable, Message{
    public static final int TYPE = 6;

    @Override
    public int getType() {
        return TYPE;
    }

}