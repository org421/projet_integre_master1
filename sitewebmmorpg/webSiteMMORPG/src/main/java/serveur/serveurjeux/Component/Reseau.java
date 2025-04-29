package serveur.serveurjeux.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Reseau {
    public static String ip = "127.0.0.1";
    public static int port = 25565;
    public static Socket socket;
    public static String UUID;
    public static String address;



    public static ObjectInputStream reader = null;
    public static ObjectOutputStream writer = null;
}
