package serveur.serveurjeux.Entity;

import serveur.serveurjeux.Controller.ActualisationAutomatique;
import serveur.serveurjeux.Controller.ControlSwitch;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.Message;
import serveur.serveurjeux.Entity.Inventaire.Inventaire;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {
    private InetAddress ip;
    private String uuid;
    private Socket socket;
    public ObjectInputStream reader = null;
    public ObjectOutputStream writer = null;
    public Personnage personnage = null;
    public Inventaire inventaire = null;
    public String pseudo;
    public boolean joueurPresent = true;
    public Thread actualisationAutomatique;


    public Client(InetAddress ip, String uuid, Socket socket) throws IOException {
        this.ip = ip;
        this.uuid = uuid;
        this.socket = socket;
        try {
            this.writer = new ObjectOutputStream(this.socket.getOutputStream());
            this.reader = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public String getPseudo() {
        return pseudo;
    }

    @Override
    public void run() {
        ActualisationAutomatique actu = new ActualisationAutomatique();
        actu.client = this;
        actualisationAutomatique = new Thread(actu);
        actualisationAutomatique.start();
        try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            InfoServeur.addNbJoueur();
            System.out.println("Nouveau client");
            Envoie envoie;
            while ((envoie = ((Envoie) reader.readObject()) ) != null) {
                for(Message message : envoie.getMessages()){
                    ControlSwitch.lecture(message.getType(),message,this,envoie);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur avec le client : " + e.getMessage());
            try {
                Clients.removeClient(this);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            joueurPresent = false;
            InfoServeur.removeNbJoueur();
            actualisationAutomatique.interrupt();
            try {
                MapChunk.mapChunks.get(personnage.getEntite().getyMatrice()/MapChunk.tailleChunks).get(personnage.getEntite().getxMatrice()/MapChunk.tailleChunks).clients.remove(this);
                MapCase.cases.get(personnage.getEntite().getyMatrice()).get(personnage.getEntite().getxMatrice()).clients.remove(this);
                socket.close();
            } catch (IOException e) {
                MapChunk.mapChunks.get(personnage.getEntite().getyMatrice()/MapChunk.tailleChunks).get(personnage.getEntite().getxMatrice()/MapChunk.tailleChunks).clients.remove(this);
                MapCase.cases.get(personnage.getEntite().getyMatrice()).get(personnage.getEntite().getxMatrice()).clients.remove(this);
                System.err.println("Impossible de fermer la connexion : " + e.getMessage());
            }
        }
    }
}
