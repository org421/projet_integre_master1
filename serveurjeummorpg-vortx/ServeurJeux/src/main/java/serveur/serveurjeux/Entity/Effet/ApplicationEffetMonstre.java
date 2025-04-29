package serveur.serveurjeux.Entity.Effet;

import serveur.serveurjeux.Controller.DegaMonstre;
import serveur.serveurjeux.Controller.DegaPersonnage;
import serveur.serveurjeux.Controller.GestionEffets;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.NPC.Monstre;

public class ApplicationEffetMonstre implements Runnable{
    public final Effet effet;
    public final Monstre monstre;
    public int boostAttaque = 1;
    public boolean running;

    public ApplicationEffetMonstre(Effet effet, Monstre monstre) {
        this.effet = effet;
        this.monstre = monstre;
        this.running = true;
    }

    public ApplicationEffetMonstre(Effet effet, Monstre monstre, int boostAttaque) {
        this.effet = effet;
        this.monstre = monstre;
        this.boostAttaque = boostAttaque;
        this.running = true;
    }

    public void stop() {  // Méthode pour arrêter le thread proprement
        running = false;
    }

    @Override
    public void run() {
        try {
            if (GestionEffets.nbEffetSurUnMonstre(monstre) > 10) {
                this.stop();
                return;
            }
            long startTime = System.currentTimeMillis();
            Monstre monstreC = monstre;
            monstreC.setPointAttaque(monstreC.getPointAttaque() + effet.getAjoutAttaque());
            monstreC.setPointArmure(monstreC.getPointArmure() + effet.getAjoutArmure());
            monstreC.setVitesse(monstreC.getVitesse() + effet.getAjoutVitesse());

            monstreC.setPointAttaque(monstreC.getPointAttaque() * effet.getMultiplicateurAttaque());
            monstreC.setPointArmure(monstreC.getPointArmure() * effet.getMultiplicateurArmure());
            monstreC.setVitesse(monstreC.getVitesse() * effet.getMultiplicateurVitesse());

            while (effet.isEffetInfini() || System.currentTimeMillis() - startTime < effet.getDuree()) {
                DegaMonstre.AppliquerDesPointDeVie(monstre, effet.getPvSec() * boostAttaque);
                if (!running) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            monstre.setPointAttaque(monstre.getPointAttaque() - effet.getAjoutAttaque());
            monstre.setPointArmure(monstre.getPointArmure() - effet.getAjoutArmure());
            monstre.setVitesse(monstre.getVitesse() - effet.getAjoutVitesse());

            monstre.setPointAttaque(monstre.getPointAttaque() / effet.getMultiplicateurAttaque());
            monstre.setPointArmure(monstre.getPointArmure() / effet.getMultiplicateurArmure());
            monstre.setVitesse(monstre.getVitesse() / effet.getMultiplicateurVitesse());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            GestionEffets.supprimerEffet(monstre, this);
        }
    }
}
