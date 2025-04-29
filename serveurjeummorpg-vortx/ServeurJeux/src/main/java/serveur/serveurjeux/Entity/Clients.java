package serveur.serveurjeux.Entity;
import serveur.serveurjeux.DTO.Reception;
import serveur.serveurjeux.DTO.typeReponse.ReponseSauvegardePersonnage;
import serveur.serveurjeux.Mapper.InventaireMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Clients {
    public static List<Client> clients = new ArrayList<>();

    public static void addClient(Client client) {
        if(!clients.contains(client)) {
            clients.add(client);
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }


    public static void removeClient(Client client) throws IOException {
        if(client != SiteWeb.client && client.pseudo != null && client.personnage != null) {
            Reception reception = new Reception();
            ReponseSauvegardePersonnage reponse = new ReponseSauvegardePersonnage();
            reponse.inventaireDTO = new InventaireMapper().inventaireToInventaireDTO(client.inventaire);
            reponse.personnage = client.personnage;
            reponse.pseudo = client.pseudo;
            reception.ajouterMessage(reponse);
            SiteWeb.client.writer.writeObject(reception);
            SiteWeb.client.writer.flush();
            SiteWeb.client.writer.reset();
        }
        client.getSocket().close();
        clients.remove(client);
    }
}
