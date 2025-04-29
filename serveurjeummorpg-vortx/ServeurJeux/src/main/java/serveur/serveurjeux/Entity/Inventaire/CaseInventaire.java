package serveur.serveurjeux.Entity.Inventaire;

import serveur.serveurjeux.Entity.Competence.Competence;
import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;

public class CaseInventaire implements Serializable {
    private Objet objet = null;
    private Competence competence = null;


    public void ajouterObjet(Objet objet) {
        this.objet = objet;
        this.competence = null;
    }

    public void ajouterCompetence(Competence competence) {
        this.competence = competence;
        this.objet = null;
    }

    public boolean isVide(){
        if (objet == null && competence == null){
            return true;
        }
        return false;
    }

    public Objet getObjet() {
        return objet;
    }


    public Competence getCompetence() {
        return competence;
    }
}
