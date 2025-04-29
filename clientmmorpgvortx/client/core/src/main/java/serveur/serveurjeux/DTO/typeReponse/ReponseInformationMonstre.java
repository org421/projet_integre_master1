package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseInformationMonstre implements Serializable, Message {
    public static final int TYPE = 15;

    public int typeMonstre;
    public float y;
    public float x;

    @Override
    public int getType() {
        return TYPE;
    }
}
