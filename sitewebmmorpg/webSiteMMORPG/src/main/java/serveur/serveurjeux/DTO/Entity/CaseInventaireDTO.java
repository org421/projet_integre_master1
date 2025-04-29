package serveur.serveurjeux.DTO.Entity;

import java.io.Serializable;

public class CaseInventaireDTO implements Serializable {
    public static int OBJET = 0;
    public static int COMPETENCE = 1;

    public int type;
    public int typeObjet;
    public int id;

}
