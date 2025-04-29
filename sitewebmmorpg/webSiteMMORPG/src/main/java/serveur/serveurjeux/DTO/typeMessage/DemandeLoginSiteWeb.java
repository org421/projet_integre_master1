package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Entity.InventaireDTO;
import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.Entity.Personnage;

import java.io.Serializable;

public class DemandeLoginSiteWeb implements Serializable, Message {
    public static final int TYPE = 5;

    public boolean valide;
    public int index;
    public Personnage personnage;
    public InventaireDTO inventaireDTO;
    public String pseudo;

    @Override
    public int getType() {
        return TYPE;
    }

}
