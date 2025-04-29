package serveur.serveurjeux.Entity.Effet;

import java.io.Serializable;

public class Effet implements Serializable {
    private int id;
    private String nom;
    private String description;
    private int ajoutAttaque = 0;
    private int ajoutArmure = 0;
    private int ajoutVitesse = 0;
    private int multiplicateurAttaque = 1;
    private int multiplicateurArmure = 1;
    private int multiplicateurVitesse = 1;
    private int pvSec = 0; //point de vie perdu/gagner par seconde (négatif si c'est des points de vie perdu)
    private boolean effetInfini = false;
    private int duree;


    public Effet(int idEffet) {
        switch (idEffet) {
            case 1:
                this.id = 1;
                this.nom = "Mort";
                this.description = "Provoque la mort";
                this.pvSec = -1;
                this.duree = 10000;
                break;
            case 2:
                this.id = 2;
                this.nom = "Heal";
                this.description = "Soigne";
                this.pvSec = 1;
                this.duree = 1500;
                break;
            case 3:
                this.id = 3;
                this.nom = "Degat instant";
                this.description = "degat";
                this.pvSec = -1;
                this.duree = 1500;
                break;
            case 4:
                this.ajoutVitesse = 1;
                this.duree = 5000;
                break;

        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAjoutAttaque() {
        return ajoutAttaque;
    }

    public void setAjoutAttaque(int ajoutAttaque) {
        this.ajoutAttaque = ajoutAttaque;
    }

    public int getAjoutArmure() {
        return ajoutArmure;
    }

    public void setAjoutArmure(int ajoutArmure) {
        this.ajoutArmure = ajoutArmure;
    }

    public int getAjoutVitesse() {
        return ajoutVitesse;
    }

    public void setAjoutVitesse(int ajoutVitesse) {
        this.ajoutVitesse = ajoutVitesse;
    }

    public int getMultiplicateurAttaque() {
        return multiplicateurAttaque;
    }

    public void setMultiplicateurAttaque(int multiplicateurAttaque) {
        this.multiplicateurAttaque = multiplicateurAttaque;
    }

    public int getMultiplicateurArmure() {
        return multiplicateurArmure;
    }

    public void setMultiplicateurArmure(int multiplicateurArmure) {
        this.multiplicateurArmure = multiplicateurArmure;
    }

    public int getMultiplicateurVitesse() {
        return multiplicateurVitesse;
    }

    public void setMultiplicateurVitesse(int multiplicateurVitesse) {
        this.multiplicateurVitesse = multiplicateurVitesse;
    }

    public int getPvSec() {
        return pvSec;
    }

    public void setPvSec(int pvSec) {
        this.pvSec = pvSec;
    }

    public boolean isEffetInfini() {
        return effetInfini;
    }

    public void setEffetInfini(boolean effetInfini) {
        this.effetInfini = effetInfini;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}
