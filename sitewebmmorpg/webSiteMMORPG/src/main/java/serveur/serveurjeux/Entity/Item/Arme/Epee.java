package serveur.serveurjeux.Entity.Item.Arme;

import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Epee implements Objet, Serializable {
    public static int type = 1;

    private int id;

    private String nom;
    private String description;

    public Epee(int idEpee) {
        switch (idEpee){
            case 1:
                this.id = 1;
                this.nom = "Bâton en bois";
                this.description = "Petit bâton facile à trouver";
                break;
        }
    }

    @Override
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
