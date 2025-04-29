package serveur.serveurjeux.Entity.Effet;

import serveur.serveurjeux.Controller.DegaPersonnage;
import serveur.serveurjeux.Controller.GestionEffets;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.NPC.Monstre;

public class ApplicationEffet implements Runnable{
    public final Effet effet;
    public final Client client;
    public int boostAttaque = 1;
    public boolean running;

    public ApplicationEffet(Effet effet, Client client) {
        this.effet = effet;
        this.client = client;
        this.running = true;
    }

    public ApplicationEffet(Effet effet, Client client, int boostAttaque) {
        this.effet = effet;
        this.client = client;
        this.boostAttaque = boostAttaque;
        this.running = true;
    }

    public void stop() {  // Méthode pour arrêter le thread proprement
        running = false;
    }

    @Override
    public void run() {
        try {
            if (GestionEffets.nbEffetSurUnClient(client) > 10) {
                this.stop();
                return;
            }
            long startTime = System.currentTimeMillis();
            Personnage personnage = client.personnage;
            personnage.getEntite().setPointAttaque(personnage.getEntite().getPointAttaque() + effet.getAjoutAttaque());
            personnage.getEntite().setPointArmure(personnage.getEntite().getPointArmure() + effet.getAjoutArmure());
            personnage.getEntite().setVitesse(personnage.getEntite().getVitesse() + effet.getAjoutVitesse());

            personnage.getEntite().setPointAttaque(personnage.getEntite().getPointAttaque() * effet.getMultiplicateurAttaque());
            personnage.getEntite().setPointArmure(personnage.getEntite().getPointArmure() * effet.getMultiplicateurArmure());
            personnage.getEntite().setVitesse(personnage.getEntite().getVitesse() * effet.getMultiplicateurVitesse());

            while (effet.isEffetInfini() || System.currentTimeMillis() - startTime < effet.getDuree()) {
                DegaPersonnage.AppliquerDesPointDeVie(client, effet.getPvSec() * boostAttaque);
                if (!running || !client.joueurPresent) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            personnage.getEntite().setPointAttaque(personnage.getEntite().getPointAttaque() - effet.getAjoutAttaque());
            personnage.getEntite().setPointArmure(personnage.getEntite().getPointArmure() - effet.getAjoutArmure());
            personnage.getEntite().setVitesse(personnage.getEntite().getVitesse() - effet.getAjoutVitesse());

            personnage.getEntite().setPointAttaque(personnage.getEntite().getPointAttaque() / effet.getMultiplicateurAttaque());
            personnage.getEntite().setPointArmure(personnage.getEntite().getPointArmure() / effet.getMultiplicateurArmure());
            personnage.getEntite().setVitesse(personnage.getEntite().getVitesse() / effet.getMultiplicateurVitesse());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            GestionEffets.supprimerEffet(client, this);
        }
    }
}
