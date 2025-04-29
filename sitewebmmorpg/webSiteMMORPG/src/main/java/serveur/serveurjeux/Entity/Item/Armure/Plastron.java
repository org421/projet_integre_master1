package serveur.serveurjeux.Entity.Item.Armure;

import serveur.serveurjeux.Entity.Item.Objet;

import java.io.Serializable;

public class Plastron implements Objet, Serializable {
    public static int type = 3;

    private int id;


    private String nom;
    private String description;

    public Plastron(int idPlastron) {
        switch (idPlastron){
            case 1:
                this.id = 1;

                this.nom = "Veste en laine";
                this.description = "petite veste contre le froid";
                break;
        }
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getType() {
        return type;
    }
}
