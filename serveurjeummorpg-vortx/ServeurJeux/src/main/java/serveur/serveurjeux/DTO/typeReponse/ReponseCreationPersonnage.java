package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.Entity.Personnage;

import java.io.Serializable;

public class ReponseCreationPersonnage implements Serializable, Message {
    public static final int TYPE = 7;

    public String pseudo;
    public Personnage personnage;

    @Override
    public int getType() {
        return TYPE;
    }
}
