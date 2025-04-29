package serveur.serveurjeux.DTO.Entity;

import java.io.Serializable;

public class InventaireDTO implements Serializable {
    public CaseInventaireDTO[][] caseInventaire = new CaseInventaireDTO[5][8];
}
