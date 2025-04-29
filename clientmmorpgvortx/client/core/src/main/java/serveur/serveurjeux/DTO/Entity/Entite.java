package serveur.serveurjeux.DTO.Entity;

import java.io.Serializable;

public class Entite implements Serializable {
    private Long id;

    private int pv;
    private int pvMax;
    private int pointArmure;
    private int pointAttaque;
    private int vitesse;
    private int level;
    private int xp;
    private int influanceKarma;
    private int xMatrice;
    private int yMatrice;
    private float x;
    private float y;


    public Entite() {}

    public Entite(int classe){
        switch(classe){
            case Personnage.GUERRIER:
                pvMax = 100;
                pv = pvMax;
                pointArmure = 100;
                pointAttaque = 100;
                vitesse = 1;
                break;
            case Personnage.ARCHER:
                pvMax = 100;
                pv = pvMax;
                pointArmure = 100;
                pointAttaque = 100;
                vitesse = 1;
                break;
            case Personnage.MAGE:
                pvMax = 100;
                pv = pvMax;
                pointArmure = 100;
                pointAttaque = 100;
                vitesse = 1;
                break;
            case Personnage.GRAPPLER:
                pvMax = 100;
                pv = pvMax;
                pointArmure = 100;
                pointAttaque = 100;
                vitesse = 1;
                break;
        }
        level = 0;
        xp = 0;
        influanceKarma = 0;
        x = 0;
        y = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getPvMax() {
        return pvMax;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
    }

    public int getPointArmure() {
        return pointArmure;
    }

    public void setPointArmure(int pointArmure) {
        this.pointArmure = pointArmure;
    }

    public int getPointAttaque() {
        return pointAttaque;
    }

    public void setPointAttaque(int pointAttaque) {
        this.pointAttaque = pointAttaque;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getInfluanceKarma() {
        return influanceKarma;
    }

    public void setInfluanceKarma(int influanceKarma) {
        this.influanceKarma = influanceKarma;
    }

    public int getxMatrice() {
        return xMatrice;
    }

    public void setxMatrice(int xMatrice) {
        this.xMatrice = xMatrice;
    }

    public int getyMatrice() {
        return yMatrice;
    }

    public void setyMatrice(int yMatrice) {
        this.yMatrice = yMatrice;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
