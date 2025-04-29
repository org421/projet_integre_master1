package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.SiteWeb;

import java.io.IOException;

public class SauvegardeAutomatique implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000*5); //5 min avant sauvegarde
                if(SiteWeb.client != null) {
                    System.out.println("Sauvegarde");
                    ActionReseau.SauvegarderToutLesClients();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
