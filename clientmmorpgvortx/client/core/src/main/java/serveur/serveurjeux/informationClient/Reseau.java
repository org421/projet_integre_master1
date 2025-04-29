package serveur.serveurjeux.informationClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Reseau {
//   public static String ip = "192.168.220.250";
//   public static String ip = "192.168.245.250";
//    public static String ip = "161.3.51.132";
//    public static String ip = "127.0.0.1";
    public static String ip = "176.133.140.142"; //ip du serveur distant
    public static int port = 25565;
    public static Socket socket;
    public static String UUID;

    public static String address;

    public static ObjectInputStream reader = null;
    public static ObjectOutputStream writer = null;

    public static String pseudo;
}
