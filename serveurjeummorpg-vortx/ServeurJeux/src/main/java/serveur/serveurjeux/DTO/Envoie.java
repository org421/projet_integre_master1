package serveur.serveurjeux.DTO;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Envoie implements Serializable {
    public String ip;
    public String pseudo;
    public String UUID;
    public int nombreMSG = 0;
    private List<Message> messages = new ArrayList<>();

    public Envoie(String ip, String pseudo, String UUID) {
        this.ip = ip;
        this.pseudo = pseudo;
        this.UUID = UUID;
    }
    public Envoie(String ip, String UUID) {
        this.ip = ip;
        this.UUID = UUID;
    }
    public Envoie(String ip) {
        this.ip = ip;
    }

    public void ajouterMessage(Message message) {
        messages.add(message);
        nombreMSG++;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
