package serveur.serveurjeux.Controller;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.Effet.ApplicationEffet;
import serveur.serveurjeux.Entity.Effet.ApplicationEffetMonstre;
import serveur.serveurjeux.Entity.Effet.Effet;
import serveur.serveurjeux.Entity.NPC.Monstre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GestionEffets {
    private static final Map<Client, List<ApplicationEffet>> effetsActifs = new ConcurrentHashMap<>();
    private static final Map<Monstre, List<ApplicationEffetMonstre>> effetsActifsMonstre = new ConcurrentHashMap<>();


    public static void ajouterEffet(Client client, ApplicationEffet effet) {
        if(!effetsActifs.containsKey(client)) {
            effetsActifs.put(client, new ArrayList<>());
        } else {
            if(effetsActifs.get(client).size() > 10){
                return;
            }
        }
        effetsActifs.get(client).add(effet);
        Thread effetThread = new Thread(effet);
        effetThread.start();
    }

    public static void ajouterEffet(Monstre monstre, ApplicationEffetMonstre effet) {
        if(!effetsActifsMonstre.containsKey(monstre)) {
            effetsActifsMonstre.put(monstre, new ArrayList<>());
        } else {
            if(effetsActifsMonstre.get(monstre).size() > 10){
                return;
            }
        }
        effetsActifsMonstre.get(monstre).add(effet);
        Thread effetThread = new Thread(effet);
        effetThread.start();
    }

    public static void supprimerEffet(Client client, Effet effet) { //si on a que l'effet
        if(!effetsActifs.containsKey(client)) {
            return;
        }

        List<ApplicationEffet> listEffetDuClient = effetsActifs.get(client);
        for(ApplicationEffet effetDuClient : listEffetDuClient){
            if(effetDuClient.effet == effet) {
                effetDuClient.stop();
                effetsActifs.get(client).remove(effetDuClient);
                break;
            }
        }

    }

    public static void supprimerEffet(Monstre monstre, Effet effet) { //si on a que l'effet
        if(!effetsActifsMonstre.containsKey(monstre)) {
            return;
        }

        List<ApplicationEffetMonstre> listEffetDuClient = effetsActifsMonstre.get(monstre);
        for(ApplicationEffetMonstre effetDuClient : listEffetDuClient){
            if(effetDuClient.effet == effet) {
                effetDuClient.stop();
                effetsActifsMonstre.get(monstre).remove(effetDuClient);
                break;
            }
        }

    }
    
    public static void supprimerEffet(Client client, ApplicationEffet effet) { //si on à le applicationEffet
        if(effetsActifs.containsKey(client)) {
            if(effetsActifs.get(client).contains(effet)) {
                effetsActifs.get(client).remove(effet);
            }
        }
    }

    public static void supprimerEffet(Monstre monstre, ApplicationEffetMonstre effet) { //si on à le applicationEffet
        if(effetsActifsMonstre.containsKey(monstre)) {
            if(effetsActifsMonstre.get(monstre).contains(effet)) {
                effetsActifsMonstre.get(monstre).remove(effet);
            }
        }
    }

    public static int nbEffetSurUnClient(Client client) {
        if(effetsActifs.containsKey(client)) {
            System.out.println(effetsActifs.get(client).size());
            return effetsActifs.get(client).size();
        }
        return 0;
    }

    public static int nbEffetSurUnMonstre(Monstre monstre) {
        if(effetsActifsMonstre.containsKey(monstre)) {
            System.out.println(effetsActifsMonstre.get(monstre).size());
            return effetsActifsMonstre.get(monstre).size();
        }
        return 0;
    }

    public static void supprimerToutLesEffetDunJoueur(Client client){
        if(!effetsActifs.containsKey(client)) {
            return;
        }

        List<ApplicationEffet> listEffetDuClient = new ArrayList<>(effetsActifs.get(client));
        for(ApplicationEffet effetDuClient : listEffetDuClient){
            effetDuClient.stop();
            effetsActifs.get(client).remove(effetDuClient);
        }
    }

    public static void supprimerToutLesEffetDunMonstre(Monstre monstre){
        if(!effetsActifsMonstre.containsKey(monstre)) {
            return;
        }

        List<ApplicationEffetMonstre> listEffetDuMonstre = new ArrayList<>(effetsActifsMonstre.get(monstre));
        for(ApplicationEffetMonstre effetDuMonstre : listEffetDuMonstre){
            effetDuMonstre.stop();
            effetsActifsMonstre.get(monstre).remove(effetDuMonstre);
        }
    }
}