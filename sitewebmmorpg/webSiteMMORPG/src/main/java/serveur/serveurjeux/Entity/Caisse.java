package serveur.serveurjeux.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Caisse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private int prixTokens;
    private String imageUrl;

    @ElementCollection
    private List<Integer> objetsIds;

    private int type;
    @ElementCollection
    private List<Integer> typeObjet;  // Liste des typeObjet (0 = Arme, 1 = Consommable, 2 = Armure)

    public Caisse() {}

    public Caisse(String nom, String description, int prixTokens, String imageUrl, List<Integer> objetsIds, int type, List<Integer> typeObjet) {
        this.nom = nom;
        this.description = description;
        this.prixTokens = prixTokens;
        this.imageUrl = imageUrl;
        this.objetsIds = objetsIds;
        this.type = type;
        this.typeObjet = typeObjet;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getTypeObjet() {
        return typeObjet;
    }

    public void setTypeObjet(List<Integer> typeObjet) {
        this.typeObjet = typeObjet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getPrixTokens() {
        return prixTokens;
    }

    public void setPrixTokens(int prixTokens) {
        this.prixTokens = prixTokens;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Integer> getObjetsIds() {
        return objetsIds;
    }

    public void setObjetsIds(List<Integer> objetsIds) {
        this.objetsIds = objetsIds;
    }
}
