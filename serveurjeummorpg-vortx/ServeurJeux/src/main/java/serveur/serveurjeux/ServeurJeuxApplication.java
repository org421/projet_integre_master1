package serveur.serveurjeux;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import serveur.serveurjeux.Controller.ActionReseau;
import serveur.serveurjeux.Controller.ActualisationAutomatique;
import serveur.serveurjeux.Controller.GestionChunk;
import serveur.serveurjeux.Controller.SauvegardeAutomatique;
import serveur.serveurjeux.Controller.VerifChunk;
import serveur.serveurjeux.Entity.*;

import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ServeurJeuxApplication implements CommandLineRunner {
	private static final int PORT = 25565;

	public static void main(String[] args) {
		Thread sauvegardeThread = new Thread(new SauvegardeAutomatique());
		Thread verifChunkThread = new Thread(new VerifChunk());
		Thread gestionChunkThread = new Thread(new GestionChunk());
		Thread actualisationEcran = new Thread(new ActualisationAutomatique());
		actualisationEcran.start();
		sauvegardeThread.start();
		verifChunkThread.start();
		gestionChunkThread.start();
		MapCase.construireMap();
		MapChunk.creerMapChunk();
		SpringApplication.run(ServeurJeuxApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Démarrage du serveur sur le port " + PORT);
		ServerSocket serverSocket = new ServerSocket(PORT);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress());
			Client newClient = new Client(clientSocket.getInetAddress(),"test",clientSocket);
			Clients.addClient(newClient);
		}
	}
}
