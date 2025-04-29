package serveur.serveurjeux.Entity.NPC.Monstres;

import serveur.serveurjeux.Controller.Deplacement;
import serveur.serveurjeux.Entity.Competence.Competence;
import serveur.serveurjeux.Entity.MapCase;
import serveur.serveurjeux.Entity.MapChunk;
import serveur.serveurjeux.Entity.NPC.Monstre;
import serveur.serveurjeux.Entity.Politiques.Attaque.Agressif;
import serveur.serveurjeux.Entity.Politiques.Attaque.Attaque;
import serveur.serveurjeux.Entity.Chunk;
import serveur.serveurjeux.Entity.Utility.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static serveur.serveurjeux.Entity.SiteWeb.client;

public class Loup implements Monstre {
    public static final int TYPE = 1;

//    private int pv = 60;
//    private int pvMax = 60;
    private int pv = 1;
    private int pvMax = 1;
    private int pointArmure = 50;
    private int pointAttaque = 150;
    private int vitesse = 2;
    private int level = 1; //A changé
    private int xp = 20; //A changé
    private int influanceKarma = 1; //A changé
    private int xMatrice;
    private int yMatrice;
    private float x;
    private float y;

    private Attaque politiqueAttaque;
    private Thread politiqueAttaqueThread;
    private List<Position> chemin = null;
    private Position cible = null;
    private List<Competence> competences = new ArrayList<>();

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

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    @Override
    public int getType() { return  TYPE; }
    @Override
    public float getX() { return x; }
    @Override
    public float getY() { return y; }
    @Override
    public void setX(float x) { this.x = x; }
    @Override
    public void setY(float y) { this.y = y; }
    @Override
    public int getVitesse() {return vitesse; }
    @Override
    public int getXMatrice() { return xMatrice; }
    @Override
    public int getYMatrice() { return yMatrice; }
    @Override
    public void setXMatrice(int xMatrice) { this.xMatrice = xMatrice; }
    @Override
    public void setYMatrice(int yMatrice) { this.yMatrice = yMatrice; }
    @Override
    public Attaque getPolitiqueAttaque(){ return politiqueAttaque; }
    @Override
    public void setPolitiqueAttaque(Attaque politiqueAttaque){ this.politiqueAttaque = politiqueAttaque; }

    public List<Position> getChemin() {
        return chemin;
    }

    public void setChemin(List<Position> chemin) {
        this.chemin = chemin;
    }

    public Position getCible() {
        return cible;
    }

    public void setCible(Position cible) {
        this.cible = cible;
    }

    public List<Competence> getCompetence(){
        return this.competences;
    }


    public Loup(Attaque politiqueAttaque, Chunk chunk) {
        System.out.println("on tente de faire apparaitre un loup");
        boolean bloque = true;

        //ajout de la compétance 1
        this.competences.add(new Competence(1));

        this.politiqueAttaque = politiqueAttaque;
//        this.politiqueAttaque.setMonstre(this);
        //On redéfinit les coordonnées d'apparition tant que le monstre est coincée
        while (bloque) {
            // on utilise ThreadLocalRandom au lieu de Random, car c'est mieux pour les programmes multi-Thread apparemment, sinon, la moitié des entiers aléatoire généré serait trop resemblant
            this.x = chunk.xStart + 1 + ThreadLocalRandom.current().nextInt(chunk.xEnd - chunk.xStart) + ThreadLocalRandom.current().nextFloat();
            this.y = chunk.yStart + 1 + ThreadLocalRandom.current().nextInt(chunk.yEnd - chunk.yStart) + ThreadLocalRandom.current().nextFloat();
//            this.x = chunk.xStart + (float)5.5;
//            this.y = chunk.yStart + (float)4.5;
            this.xMatrice = (int) Math.floor(this.x);
            this.yMatrice = (int) Math.floor(this.y);

            if ( !Deplacement.isObstacle(MapCase.cases.get(this.yMatrice).get(this.xMatrice).symbole-'0') ){
                bloque = false;
            } else {
                System.out.println(this.xMatrice + " " + this.yMatrice + " : bloque");
            }

        }

        MapCase.cases.get(this.yMatrice).get(this.xMatrice).monstres.add(this);
        MapChunk.mapChunks.get((int) Math.floor(this.y/32)).get((int) Math.floor(this.x/32)).monstres.add(this);

//        this.demmarerPolitiqueAttaque(); //on lance le thread à la fin
        System.out.println("loup apparu");
    }

    @Override
    public void demmarerPolitiqueAttaque(){
        switch (politiqueAttaque.getType()) {
            case 1:
                politiqueAttaqueThread = new Thread(this.politiqueAttaque);
                politiqueAttaqueThread.start();
                break;
        }
    }
    @Override
    public void arreterPolitiqueAttaque(){
        if(politiqueAttaqueThread.isAlive()){
            System.out.println("thread vivant : on l'arrête");
            politiqueAttaque.stopping(politiqueAttaqueThread);
        } else {
            System.out.println("thread non-vivant");
        }
    }


    public void lancement(){
        this.politiqueAttaque.setMonstre(this);
        this.demmarerPolitiqueAttaque();
    }


}
