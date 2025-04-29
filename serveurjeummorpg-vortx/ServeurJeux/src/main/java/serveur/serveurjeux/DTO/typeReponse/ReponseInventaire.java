package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Client.InventaireDTOClient;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.Entity.Inventaire.Inventaire;

import java.io.Serializable;

public class ReponseInventaire implements Serializable, Message {

    public static final int TYPE = 13;

    public InventaireDTOClient inventaire;

    @Override
    public int getType() {
        return TYPE;
    }
}