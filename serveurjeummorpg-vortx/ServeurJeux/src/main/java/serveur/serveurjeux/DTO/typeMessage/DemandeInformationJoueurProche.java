package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class DemandeInformationJoueurProche implements Serializable, Message {
    public static final int TYPE = 10;

    @Override
    public int getType() {
        return TYPE;
    }
}
