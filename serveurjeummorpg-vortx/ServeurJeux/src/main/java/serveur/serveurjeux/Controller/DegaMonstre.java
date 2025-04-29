package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.NPC.Monstre;

public class DegaMonstre {

    public static void AppliquerDesPointDeVie(Monstre monstre, int pointVie){
        if(pointVie < 0){
            pointVie = pointVie / monstre.getPointArmure();
            if(pointVie < 1){
                pointVie = -1;
            }
        }

        if(monstre.getPv() + pointVie <= 0){
            monstre.setPv(0);
            MortMonstre.gererMortMonstre(monstre);
        } else if (monstre.getPv() + pointVie > monstre.getPvMax()) {
            monstre.setPv(monstre.getPvMax());
        } else {
            monstre.setPv(monstre.getPv() + pointVie);
        }
    }
}
