package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.Entity.Personnage;

import java.io.Serializable;

public class ReponseInformationPersonnage implements Serializable, Message {
    public static final int TYPE = 5;

    public Personnage personnage;

    @Override
    public int getType() {
        return TYPE;
    }
}
