package serveur.serveurjeux.Entity.Item.Consommable;

import serveur.serveurjeux.Entity.Effet.Effet;
import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Potion implements Objet, Serializable {
    public static int type = 6;

    private int id;
    private String nom;
    private String description;
    private int niveau;
    private int valeur;
    public List<Effet> effets = new ArrayList<>();


    public Potion(int idPotion) {
        switch (idPotion){
            case 1:
                this.id = 1;
                this.nom = "Potion de soin";
                this.description = "Elle soigne le joueur ayant consommé cette potion";
                this.niveau = 0;
                this.valeur = 1;
                this.effets.add(new Effet(3));
                break;
            case 2:
                this.id = 2;
                this.nom = "Potion de vitesse";
                this.description = "La vitesse du joueur ayant consommé cette potion augmente";
                this.niveau = 0;
                this.valeur = 1;
                this.effets.add(new Effet(4)); // Effet de vitesse
                break;

        }

    }

    public static void setType(int type) {
        Potion.type = type;
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
