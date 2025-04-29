package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

    public class DemandeCreationPersonnage implements Serializable, Message {
    public static final int TYPE = 7;

    public int classPersonnage;

    @Override
    public int getType() {
        return TYPE;
    }

}
