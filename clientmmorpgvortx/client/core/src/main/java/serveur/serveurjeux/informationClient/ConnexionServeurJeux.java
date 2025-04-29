package serveur.serveurjeux.informationClient;


import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.DTO.Reception;
import serveur.serveurjeux.DTO.typeMessage.DemandeUUID;
import serveur.serveurjeux.controller.ControlSwitch;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnexionServeurJeux{

    public void run(String... args) {
        ThreadConnexion threadConnexion = new ThreadConnexion();
        threadConnexion.start();
    }
    class ThreadEcouteur extends Thread {

        @Override
        public void run() {
            System.out.println("Ecouteur serveur");
            Reception reception;
            while (true) {
                try {
                    while ((reception = ((Reception) Reseau.reader.readObject())) != null) {
                    //    System.out.println("Nouveau msg");
                        for (Message message : reception.getMessages()) {
                    //        System.out.println("Message type: " + message.getType());
                            try {
                                ControlSwitch.lecture(message.getType(), message);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Erreur ecoute serveur");
                    ThreadConnexion threadConnexion = new ThreadConnexion();
                    threadConnexion.start();
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
