package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseDeplacement implements Serializable, Message {
    public static final int TYPE = 8;
    public float x;
    public float y;
    public int direction;

    @Override
    public int getType() {
        return TYPE;
    }

}
