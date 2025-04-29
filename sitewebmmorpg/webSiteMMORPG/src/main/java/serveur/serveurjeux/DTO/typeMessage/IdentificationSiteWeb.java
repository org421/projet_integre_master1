package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class IdentificationSiteWeb implements Serializable, Message {
    public static final int TYPE = 3;

    @Override
    public int getType() {
        return TYPE;
    }
}
