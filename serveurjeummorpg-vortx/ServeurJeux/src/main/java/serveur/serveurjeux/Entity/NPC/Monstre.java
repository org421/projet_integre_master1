package serveur.serveurjeux.Entity.NPC;

import serveur.serveurjeux.Entity.Competence.Competence;
import serveur.serveurjeux.Entity.Politiques.Attaque.Attaque;
import serveur.serveurjeux.Entity.Utility.Position;

import java.util.List;

public interface Monstre {
    int getType();

    int getPv();
    void setPv(int pv);
    int getPvMax();
    void setPvMax(int pvMax);
    int getPointArmure();
    void setPointArmure(int pointArmure);
    int getPointAttaque();
    void setPointAttaque(int pointAttaque);
    void setVitesse(int vitesse);

    float getX();
    float getY();
    void setX(float x);
    void setY(float y);
    int getVitesse();
    int getXMatrice();
    int getYMatrice();
    void setXMatrice(int xMatrice);
    void setYMatrice(int yMatrice);
    Attaque getPolitiqueAttaque();
    void setPolitiqueAttaque(Attaque attaque);
    List<Position> getChemin();
    void setChemin(List<Position> chemin);
    Position getCible();
    void setCible(Position cible);
    List<Competence> getCompetence();

    void demmarerPolitiqueAttaque();
    void arreterPolitiqueAttaque();
}
