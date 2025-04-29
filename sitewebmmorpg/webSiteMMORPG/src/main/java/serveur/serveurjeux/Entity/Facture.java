package serveur.serveurjeux.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User client;

    @ManyToOne
    private TokenPack tokenPack;  // 📌 Remplace l'ancien idRestaurant

    private int prixTTC;
    private LocalDate dateFacture;

    public Facture() {
        this.dateFacture = LocalDate.now();
    }

    public Facture(User client, TokenPack tokenPack) {
        this.client = client;
        this.tokenPack = tokenPack;
        this.prixTTC = tokenPack.getPrice();
        this.dateFacture = LocalDate.now();
    }

    public Long getId() { return id; }
    public User getClient() { return client; }
    public TokenPack getTokenPack() { return tokenPack; }
    public int getPrixTTC() { return prixTTC; }
    public LocalDate getDateFacture() { return dateFacture; }
}
