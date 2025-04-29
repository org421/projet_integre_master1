package serveur.serveurjeux.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class CaseInventaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventaire_id", nullable = false)
    @JsonIgnore
    private Inventaire inventaire;

    public static int OBJET = 0;
    public static int COMPETENCE = 1;

    public int type;
    public int typeObjet;
    public int idJeux;
    public int indexY;
    public int indexX;

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public static void setOBJET(int OBJET) {
        CaseInventaire.OBJET = OBJET;
    }



    public static void setCOMPETENCE(int COMPETENCE) {
        CaseInventaire.COMPETENCE = COMPETENCE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeObjet() {
        return typeObjet;
    }

    public void setTypeObjet(int typeObjet) {
        this.typeObjet = typeObjet;
    }

    public int getIdJeux() {
        return idJeux;
    }

    public void setIdJeux(int idJeux) {
        this.idJeux = idJeux;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }
}
