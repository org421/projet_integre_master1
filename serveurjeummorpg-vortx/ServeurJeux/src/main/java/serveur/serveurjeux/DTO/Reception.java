package serveur.serveurjeux.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reception implements Serializable {
    public int nombreMSG = 0;
    private List<Message> messages = new ArrayList<>();

    public void ajouterMessage(Message message) {
        messages.add(message);
        nombreMSG++;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
