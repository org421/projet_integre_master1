package serveur.serveurjeux.Entity.Competence;

import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.Effet.Effet;
import serveur.serveurjeux.Entity.Item.Objet;
import serveur.serveurjeux.Entity.NPC.Monstre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Competence implements Serializable {
    private int id;
    private String nom;
    private String description;
    private List<Effet> effets = new ArrayList<>();
    private int cooldown;
    private int rayon;
    private int duree;
    private int niveau;
    private int distanceDuJoueur;
    private int vitesse;
    public Thread paterne;
    public Client client;
    public Monstre monstre;
    public int direction;
    public float positionX;
    public float positionY;
    public HashMap<Integer, List<Integer>> listCoord = null;
    public List<Client> clientsToucher = new ArrayList<>();
    public List<Monstre> monstresToucher = new ArrayList<>();

    public Competence(int idCompetence) {
        switch (idCompetence) {
            case 1:
                this.id = 1;
                this.nom = "Competence test";
                this.description = "Competence test";
                this.cooldown = 1;
                this.rayon = 1;
                this.duree = 10000;
                this.niveau = 1;
                this.distanceDuJoueur = 1;
                this.vitesse = 1;
                this.effets = new ArrayList<>();
                effets.add(new Effet(1));
                this.paterne = new Thread(new PetiteAttaqueDevantJoueur(this));
                break;
            case 2:
                this.id = 2;
                this.nom = "Laser";
                this.description = "Laser qui provoque des dégats instantanés";
                this.cooldown = 3;
                this.rayon = 8;
                this.duree = 10000;
                this.niveau = 3;
                this.distanceDuJoueur = 1;
                this.vitesse = 1;
                this.effets = new ArrayList<>();
                effets.add(new Effet(3));
                this.paterne = new Thread(new AttaqueDistanceInstantanee(this));
                break;
            case 3:
                this.id = 3;
                this.nom = "Boule de feu";
                this.description = "Boule de feu qui provoque des dégats";
                this.cooldown = 3;
                this.rayon = 5;
                this.duree = 5000;
                this.niveau = 1;
                this.distanceDuJoueur = 1;
                this.vitesse = 1;
                this.effets = new ArrayList<>();
                effets.add(new Effet(3));
                this.paterne = new Thread(new AttaqueDistanceInstantanee(this));
                break;
            case 4:
                this.id = 4;
                this.nom = "Zone de dégats";
                this.description = "Zone de dégats qui provoque des dégats autour du joueur";
                this.cooldown = 15;
                this.rayon = 3;
                this.duree = 3000;
                this.niveau = 1;
                this.distanceDuJoueur = 1;
                this.vitesse = 1;
                this.effets = new ArrayList<>();
                effets.add(new Effet(3));
                this.paterne = new Thread(new AttaqueZoneAutourJoueur(this));
                break;
            case 5:
                this.id = 5;
                this.nom = "Zone de soin";
                this.description = "Zone de soin qui provoque des soins autour du joueur";
                this.cooldown = 15;
                this.rayon = 3;
                this.duree = 3000;
                this.niveau = 1;
                this.distanceDuJoueur = 1;
                this.vitesse = 1;
                this.effets = new ArrayList<>();
                effets.add(new Effet(2));
                this.paterne = new Thread(new AttaqueZoneAutourJoueur(this));
                break;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Effet> getEffets() {
        return effets;
    }

    public void setEffets(List<Effet> effets) {
        this.effets = effets;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getDistanceDuJoueur() {
        return distanceDuJoueur;
    }

    public void setDistanceDuJoueur(int distanceDuJoueur) {
        this.distanceDuJoueur = distanceDuJoueur;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
}
