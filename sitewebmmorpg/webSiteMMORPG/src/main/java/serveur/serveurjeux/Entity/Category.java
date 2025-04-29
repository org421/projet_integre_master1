package serveur.serveurjeux.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    public static final int CATEGORIE1 = 0;
    public static final int CATEGORIE2 = 1;
    public static final int CATEGORIE3 = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

        private int numCategorie;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Topic> topics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public int getNumCategorie() {
        return numCategorie;
    }

    public void setNumCategorie(int numCategorie) {
        this.numCategorie = numCategorie;
    }
}
