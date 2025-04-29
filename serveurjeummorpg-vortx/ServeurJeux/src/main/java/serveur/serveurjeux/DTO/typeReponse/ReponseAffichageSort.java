package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Entity.Sort;
import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseAffichageSort implements Serializable, Message {
    public static final int TYPE = 14;

    public Sort sort;






    @Override
    public int getType() {
        return TYPE;
    }
}
