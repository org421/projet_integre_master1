package serveur.serveurjeux.DTO.typeMessage;

import serveur.serveurjeux.DTO.Message;

import java.io.Serializable;

public class DemandeChangementCaseInventaire implements Serializable, Message {
    public static final int TYPE = 9;

    public int indexYDepart;
    public int indexXDepart;
    public int indexYArrivee;
    public int indexXArrivee;

    @Override
    public int getType() {
        return TYPE;
    }
}
