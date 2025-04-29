package serveur.serveurjeux.DTO.typeReponse;

import serveur.serveurjeux.DTO.Entity.InventaireDTO;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class ReponseSauvegardePersonnage implements Serializable, Message {
    public static final int TYPE = 12;

    public String pseudo;
    public Personnage personnage;
    public InventaireDTO inventaireDTO;

    @Override
    public int getType() {
        return TYPE;
    }
}
