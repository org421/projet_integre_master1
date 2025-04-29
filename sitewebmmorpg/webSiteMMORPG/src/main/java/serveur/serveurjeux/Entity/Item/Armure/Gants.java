package serveur.serveurjeux.Entity.Item.Armure;

import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;

public class Gants implements Objet, Serializable {
    public static int type = 5;

    private int id;

    private String nom;
    private String description;

    public Gants(int idGants) {
        switch (idGants){
            case 1:
                this.id = 1;
                this.nom = "Gants en laine";
                this.description = "petite paire de gant";
                break;
        }
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

    public static void setType(int type) {
        Gants.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getType() {
        return type;
    }
}
