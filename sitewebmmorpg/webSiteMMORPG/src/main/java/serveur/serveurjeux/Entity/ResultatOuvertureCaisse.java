package serveur.serveurjeux.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class ResultatOuvertureCaisse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int caisseId;

    private String itemName;

    private int typeObjet;

    private String itemImage;

    private int itemType;

    private Long userId;

    public int getTypeObjet() {
        return typeObjet;
    }

    public void setTypeObjet(int typeObjet) {
        this.typeObjet = typeObjet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCaisseId() {
        return caisseId;
    }

    public void setCaisseId(int caisseId) {
        this.caisseId = caisseId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}

