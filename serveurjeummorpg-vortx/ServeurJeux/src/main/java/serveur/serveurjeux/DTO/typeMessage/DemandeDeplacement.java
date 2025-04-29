package serveur.serveurjeux.DTO.typeMessage;


import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class DemandeDeplacement implements Serializable, Message {
    public static final int TYPE = 8;
    public int direction;


    @Override
    public int getType() {
        return TYPE;
    }





}
