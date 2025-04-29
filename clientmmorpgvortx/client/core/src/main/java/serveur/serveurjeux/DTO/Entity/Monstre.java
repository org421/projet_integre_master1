package serveur.serveurjeux.DTO.Entity;

//Class monstre DIFFERENT de celui du serveur mais qui contiendra le meme monstre
public class Monstre {
    private int typeMonstre;
    private float x;
    private float y;

    public Monstre(int typeMonstre, float x, float y) {
        this.typeMonstre = typeMonstre;
        this.x = x;
        this.y = y;
    }

    public int getTypeMonstre() {
        return typeMonstre;
    }

    public void setTypeMonstre(int typeMonstre) {
        this.typeMonstre = typeMonstre;
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
