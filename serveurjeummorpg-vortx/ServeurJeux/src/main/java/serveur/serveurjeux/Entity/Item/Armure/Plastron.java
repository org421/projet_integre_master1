package serveur.serveurjeux.Entity.Item.Armure;

import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;

public class Plastron implements Objet, Serializable {
    public static int type = 3;

    private int id;
    private String nom;
    private String description;
    private int niveau;
    private int bonusArmure;
    private int valeur;
    //ajouter les effets


    public Plastron(int idPlastron) {
        switch (idPlastron){
            case 1:
                this.id = 1;
                this.nom = "Veste en laine";
                this.description = "petite veste contre le froid";
                this.niveau = 0;
                this.bonusArmure = 1;
                this.valeur = 5;
                break;
        }
    }

    public static void setType(int type) {
        Plastron.type = type;
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
