package serveur.serveurjeux.Entity.Item.Armure;

import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;

public class Casque implements Objet, Serializable {
    public static int type = 2;

    private int id;
    private String nom;
    private String description;
    private int niveau;
    private int bonusArmure;
    private int valeur;
    //ajouter les effets


    public Casque(int idCasque) {
        switch (idCasque){
            case 1:
                this.id = 1;
                this.nom = "Bandana";
                this.description = "petit bandana, protège un peu du soleil";
                this.niveau = 0;
                this.bonusArmure = 1;
                this.valeur = 3;
                break;
        }
    }

    public static void setType(int type) {
        Casque.type = type;
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

    public int getBonusArmure() {
        return bonusArmure;
    }

    public void setBonusArmure(int bonusArmure) {
        this.bonusArmure = bonusArmure;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    @Override
    public int getType() {
        return type;
    }
}
