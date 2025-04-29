package serveur.serveurjeux.Entity.Utility;

public class Pile {
    Object[] elem;
    int taille;

    final int MAX_PILE = 10000;

    public Pile() {
        this.elem = new Object[MAX_PILE];
        this.taille = 0;
    }

    public Object getSommet() {
        if(!this.videPile())
            return this.elem[taille-1];
        return null;
    }
    public int getTaille() {
        return this.taille;
    }

    public boolean videPile() {
        if(this.taille == 0) {return true;}
        else {return false;}
    }

    public void viderPile() {
        while(!this.videPile()){
            this.depiler();
        }
    }

    public void empiler(Object o) {
        if(taille == MAX_PILE) {/*System.out.println("Pile pleine");*/ return ;}
        elem[taille]=o;
        taille++;
    }

    public void empiler(Object[] o) {
        if(taille == MAX_PILE) {/*System.out.println("Pile pleine");*/ return ;}
        for(int i = 0; i < o.length; i++) {
            elem[taille] = o[i];
            taille++;
        }
    }

    public Object depiler () {
        Object res;

        if(!this.videPile()) {
            res=this.getSommet();
            taille--;
            return res;
        }
        return null;
    }

    public boolean contient(Object o) {
        if(!this.videPile()) {
            for(int i=0; i<taille; i++) {
                if(((Position)elem[i]).equals(((Position)o))) {
                    //System.out.println(o+"=="+elem[i]+": "+((Position)elem[i]).equals(((Position)o)));
                    return true;
                }
            }
        }
        return false;
    }

    public Pile copy(){
        Pile copy = new Pile();
        int i;

        for(i = 0; i < taille; i++) {
            copy.empiler(this.elem[i]);
        }

        return copy;
    }

    public String toString() {
        int i;
        String restePile ="";

        if(this.videPile()) {return "Pile vide";}
        for(i=taille-2; i>=0; i--) {restePile += elem[i].toString()+"\n";}
        return "sommet = \n"+this.getSommet().toString()+"\nreste = \n"+restePile;
    }
}