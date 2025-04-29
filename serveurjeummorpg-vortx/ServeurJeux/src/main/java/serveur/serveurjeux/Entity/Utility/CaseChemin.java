package serveur.serveurjeux.Entity.Utility;

import java.util.Objects;

public class CaseChemin {
    private Position position;
    private int pere; //index du pere dans une liste de position
    private int prec; //index du predecesseur dans une liste de position
    private int cout; //Cout de déplacement pour aller sur cette case

    public CaseChemin(Position position, int pere, int prec, int cout) {
        this.position = position;
        this.pere = pere;
        this.prec = prec;
        this.cout = cout;
    }

    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public int getPere() {return pere;}
    public void setPere(int pere) {this.pere = pere;}
    public int getPrec() {return prec;}
    public void setPrec(int prec) {this.prec = prec;}
    public int getCout() {return cout;}
    public void setCout(int cout) {this.cout = cout;}



    // ATTENTION : Modification de List<CaseChemin> list.contains(...) (on compare, avec ca, les position uniquement)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CaseChemin other = (CaseChemin) obj;
//        System.out.println("Comparaison : " + this.position + " avec " + other.position);
//        System.out.println(this.position.equals(other.position));
        return this.position.equals(other.position); // Compare uniquement la position
    }

    @Override
    public int hashCode() {
        return Objects.hash(position); // Hash basé sur la position
    }

}
