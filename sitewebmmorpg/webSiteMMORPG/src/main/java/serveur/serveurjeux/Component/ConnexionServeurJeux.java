package serveur.serveurjeux.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.Reception;
import serveur.serveurjeux.DTO.typeMessage.DemandeUUID;
import serveur.serveurjeux.DTO.typeMessage.IdentificationSiteWeb;
import serveur.serveurjeux.controllerReseau.ControlSwitch;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@Component
public class ConnexionServeurJeux implements CommandLineRunner {
    private long serverStartTime; // Temps de démarrage du serveur
    private boolean time;

    @Autowired
    ControlSwitch controlSwitch;

    @Override
    public void run(String... args) {
        ThreadConnexion threadConnexion = new ThreadConnexion();
        threadConnexion.start();
    }

    public String getUptime() {
        if (serverStartTime == 0 && time == false) {
            return "Le serveur n'est pas encore démarré.";
        }

        long currentTime = System.currentTimeMillis();
        long uptimeMillis = currentTime - serverStartTime;

        long seconds = (uptimeMillis / 1000) % 60;
        long minutes = (uptimeMillis / (1000 * 60)) % 60;
        long hours = uptimeMillis / (1000 * 60 * 60);

        return hours + "h " + minutes + "m " + seconds + "s";
    }

    public boolean isServerOnline() {
        if(Reseau.UUID != null){
            return true;
        }
        return false;
    }

    class ThreadEcouteur extends Thread {

        @Override
        public void run() {
            System.out.println("Ecouteur serveur");
            Reception reception;
            while (true) {
                try {
                    while ((reception = ((Reception) Reseau.reader.readObject())) != null) {
                        System.out.println("Nouveau msg");
                        for (Message message : reception.getMessages()) {
                            System.out.println("Message type: " + message.getType());
                            try {
                                controlSwitch.lecture(message.getType(), message);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Erreur ecoute serveur");
                    e.printStackTrace();
                    ThreadConnexion threadConnexion = new ThreadConnexion();
                    threadConnexion.start();
                    time = false;
                    this.interrupt();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ThreadConnexion extends Thread {
        private Socket socket;
        ThreadEcouteur threadEcouteur = new ThreadEcouteur();

        @Override
        public void run() {
            System.out.println("Connexion serveur");
            Boolean connected = false;
            try {
                Reseau.address = InetAddress.getLocalHost().getHostAddress();
                Reseau.UUID = null;

            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            while (!connected) {
                try {
                    socket = new Socket(Reseau.ip, Reseau.port);
                    Reseau.socket = socket;
                    Reseau.reader = new ObjectInputStream(this.socket.getInputStream());
                    Reseau.writer = new ObjectOutputStream(this.socket.getOutputStream());
                    System.out.println("Connexion au serveur !");
                    connected = true;
                    serverStartTime = System.currentTimeMillis(); // Enregistre le temps de connexion
                    threadEcouteur.start();
                } catch (IOException e) {
                    System.err.println("Erreur : " + e.getMessage());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                System.out.println(connected);
            }

            if (Reseau.UUID == null){
                Envoie envoie = new Envoie(Reseau.address);
                envoie.ajouterMessage(new DemandeUUID());
                envoie.ajouterMessage(new IdentificationSiteWeb());
                try {
                    Reseau.writer.writeObject(envoie);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Reseau.writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            this.interrupt();
        }
    }
}
