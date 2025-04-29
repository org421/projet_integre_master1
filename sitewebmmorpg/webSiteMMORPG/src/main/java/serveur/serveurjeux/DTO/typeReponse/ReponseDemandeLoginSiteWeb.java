package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseDemandeLoginSiteWeb implements Serializable, Message {
    public static final int TYPE = 4;
    public String pseudo;
    public String mdp;
    public int index;

    @Override
    public int getType() {
        return TYPE;
    }
}
