package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.Client;

public class DegaPersonnage {

    public static void AppliquerDesPointDeVie(Client client, int pointVie){
        if(pointVie < 0){
            pointVie = pointVie / client.personnage.getEntite().getPointArmure();
            if(pointVie < 1){
                pointVie = -1;
            }
        }


        if(client.personnage.getEntite().getPv() + pointVie <= 0){
            client.personnage.getEntite().setPv(0);
            MortPersonnage.gererMortPersonnage(client);
        } else if (client.personnage.getEntite().getPv() + pointVie > client.personnage.getEntite().getPvMax()) {
            client.personnage.getEntite().setPv(client.personnage.getEntite().getPvMax());
        } else {
            client.personnage.getEntite().setPv(client.personnage.getEntite().getPv() + pointVie);
        }
    }
}
