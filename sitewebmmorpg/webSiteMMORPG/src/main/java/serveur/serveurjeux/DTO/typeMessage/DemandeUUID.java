package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class DemandeUUID implements Serializable, Message {
    public static final int TYPE = 2;

    @Override
    public int getType() {
        return TYPE;
    }
}
