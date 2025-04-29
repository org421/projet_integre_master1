package serveur.serveurjeux.Entity.Item.Arme;

import serveur.serveurjeux.Entity.Effet.Effet;
import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Epee implements Objet, Serializable {
    public static int type = 1;

    private int id;
    private String nom;
    private String description;
    private int niveau;
    private int bonusAttaque;
    private int valeur;
    private List<Effet> effets = new ArrayList<>();
    //ajouter les effets


    public Epee(int idEpee) {
        switch (idEpee){
            case 1:
                this.id = 1;
                this.nom = "Bâton en bois";
                this.description = "Petit bâton facile à trouver";
                this.niveau = 0;
                this.bonusAttaque = 1;
                this.valeur = 2;
                break;
        }
    }


    public static void setType(int type) {
        Epee.type = type;
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

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getBonusAttaque() {
        return bonusAttaque;
    }

    public void setBonusAttaque(int bonusAttaque) {
        this.bonusAttaque = bonusAttaque;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public List<Effet> getEffets() {
        return effets;
    }

    public void setEffets(List<Effet> effets) {
        this.effets = effets;
    }

    @Override
    public int getType() {
        return type;
    }
}
