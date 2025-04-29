package serveur.serveurjeux.Entity;

import jakarta.persistence.*;

@Entity
public class TokenPack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int tokens;
    private int price; // 👈 Prix en centimes (plus sûr que float/double)

    public TokenPack() {}

    public TokenPack(String name, int tokens, int price) {
        this.name = name;
        this.tokens = tokens;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getTokens() { return tokens; }
    public int getPrice() { return price; } // 👈 Retourne le prix en centimes

    public void setName(String name) { this.name = name; }
    public void setTokens(int tokens) { this.tokens = tokens; }
    public void setPrice(int price) { this.price = price; }
}
