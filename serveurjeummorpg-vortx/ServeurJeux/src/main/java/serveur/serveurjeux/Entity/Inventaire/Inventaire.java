package serveur.serveurjeux.Entity.Inventaire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventaire implements Serializable {
    public CaseInventaire[][] inventaire = new CaseInventaire[5][8];
}
