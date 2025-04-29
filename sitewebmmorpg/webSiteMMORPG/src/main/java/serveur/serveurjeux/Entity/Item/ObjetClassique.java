package serveur.serveurjeux.Entity.Item;

import java.io.Serializable;

public class ObjetClassique implements Objet, Serializable {
    public static int type = 7;

    private int id;
    private String nom;
    private String description;
    private int valeur;


    public ObjetClassique(int idObjectClassique) {
        switch (idObjectClassique){
            case 1:
                this.id = 1;
                this.nom = "Caillou";
                this.description = "petit caillou";
                this.valeur = 0;
                break;
        }
    }


    @Override
    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
