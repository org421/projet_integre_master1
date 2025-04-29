package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseDeplacementAutreJoueur implements Serializable, Message {
    public static final int TYPE = 9;

    public Personnage personnage;
    public int direction;

    @Override
    public int getType() {
        return TYPE;
    }


}
