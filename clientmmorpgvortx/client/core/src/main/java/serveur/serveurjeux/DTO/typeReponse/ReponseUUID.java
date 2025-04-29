package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseUUID implements Serializable, Message {
    public static final int TYPE = 2;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public ReponseUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
