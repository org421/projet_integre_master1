package serveur.serveurjeux.informationClient;

import com.badlogic.gdx.math.Vector2;

public class InformationClient {
    //       ==============================
    //       = Information pour le client =
    //       =      Lié au joueur         =
    //       ==============================

    private String pseudo = "";                                              // Pseudo du joueur connecté au client
    private int pointsDAttaque = 0;                                          // Point d'attaque du joueur selon son level et classe
    private int pointsDArmure = 0;                                           // Point d'armure du joueur selon son level et classe
    private int pointsDeVie = 0;                                             // Point de vie actuel du joueur
    private int pointsDeVieMax = 0;                                          // Point de vie max
    protected Vector2 position = new Vector2(0,0);                     // Position en float
    int mapX = 0;                                                            // Position matricielle Y
    int mapY = 0;                                                            // Position matricielle Y
    private float vitesse = 0;                                               // Vitesse de l'entité
    private boolean enVie = false;                                           // Si le personnage est en vie ou pas
    private String guilde = "";                                              // Guilde du joueur
    private int niveau = 0;                                                  // Niveau total du joueur
    private float xp = 0;                                                    // Xp du joeur
    private int classe = 0;                                                  // Classe du joueur
    private int attaqueTotale = 0;                                           // Attribut pour l'attaque totale du joueur
    private int armureTotale = 0;                                            // Attribut pour l'armure totale du joueur
    private int argent = 0;                                                  // Argent du joueur


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getPointsDAttaque() {
        return pointsDAttaque;
    }

    public void setPointsDAttaque(int pointsDAttaque) {
        this.pointsDAttaque = pointsDAttaque;
    }

    public int getPointsDArmure() {
        return pointsDArmure;
    }

    public void setPointsDArmure(int pointsDArmure) {
        this.pointsDArmure = pointsDArmure;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    public int getPointsDeVieMax() {
        return pointsDeVieMax;
    }

    public void setPointsDeVieMax(int pointsDeVieMax) {
        this.pointsDeVieMax = pointsDeVieMax;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

    public boolean isEnVie() {
        return enVie;
    }

    public void setEnVie(boolean enVie) {
        this.enVie = enVie;
    }

    public String getGuilde() {
        return guilde;
    }

    public void setGuilde(String guilde) {
        this.guilde = guilde;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public float getXp() {
        return xp;
    }

    public void setXp(float xp) {
        this.xp = xp;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getAttaqueTotale() {
        return attaqueTotale;
    }

    public void setAttaqueTotale(int attaqueTotale) {
        this.attaqueTotale = attaqueTotale;
    }

    public int getArmureTotale() {
        return armureTotale;
    }

    public void setArmureTotale(int armureTotale) {
        this.armureTotale = armureTotale;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }
}
