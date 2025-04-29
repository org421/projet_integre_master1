package serveur.serveurjeux.Entity.Item.Armure;

import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;

public class Bottes implements Objet, Serializable {
    public static int type = 2; // Type d'objet : Armure

    private int id;
    private String nom;
    private String description;

    public Bottes(int idBottes) {
        switch (idBottes) {
            case 1:
                this.id = 1;

                this.nom = "Bottes en cuire";
                this.description = "petite paire de botte en cuire";
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
