package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class DemandeCaseSelection implements Serializable, Message {
    public static final int TYPE = 13;

    public int direction;
    public int indexX;
    public int indexY;

    @Override
    public int getType() {
        return TYPE;
    }




}
