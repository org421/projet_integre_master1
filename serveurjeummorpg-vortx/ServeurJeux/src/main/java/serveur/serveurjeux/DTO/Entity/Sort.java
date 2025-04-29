package serveur.serveurjeux.DTO.Entity;

import java.io.Serializable;

public class Sort implements Serializable {

    float x;
    float y;
    int id_competence;


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

    public int getId_competence() {
        return id_competence;
    }

    public void setId_competence(int id_competence) {
        this.id_competence = id_competence;
    }
}
