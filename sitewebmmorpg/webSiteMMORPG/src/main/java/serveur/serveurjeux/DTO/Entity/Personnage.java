package serveur.serveurjeux.DTO.Entity;

import java.io.Serializable;

public class Personnage implements Serializable {
    public static final int GUERRIER = 0;
    public static final int ARCHER = 1;
    public static final int MAGE = 2;
    public static final int GRAPPLER = 3;

    private Long id;
    private Entite entite;
    private int classe;
    private int argent;
    private int rage;
    private int karma;
    //    @ManyToOne
//    private Guilde guilde;
//    @ManyToOne
//    private Inventaire inventaire;
//    @ManyToOne
//    private Groupe groupe;
    private int pointPersonnage;
    private int pointCompetence;

    public Personnage(int classe) {
        switch (classe) {
            case GUERRIER:
                entite = new Entite(GUERRIER);
                break;
            case ARCHER:
                entite = new Entite(ARCHER);
                break;
            case MAGE:
                entite = new Entite(MAGE);
                break;
            case GRAPPLER:
                entite = new Entite(GRAPPLER);
                break;
            default:
                break;
        }
        argent = 0;
        karma = 0;
        pointPersonnage = 0;
        pointCompetence = 0;
    }

    public Personnage() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entite getEntite() {
        return entite;
    }

    public void setEntite(Entite entite) {
        this.entite = entite;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public int getPointPersonnage() {
        return pointPersonnage;
    }

    public void setPointPersonnage(int pointPersonnage) {
        this.pointPersonnage = pointPersonnage;
    }

    public int getPointCompetence() {
        return pointCompetence;
    }

    public void setPointCompetence(int pointCompetence) {
        this.pointCompetence = pointCompetence;
    }
}
