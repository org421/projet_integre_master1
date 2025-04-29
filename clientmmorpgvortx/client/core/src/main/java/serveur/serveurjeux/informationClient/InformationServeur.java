package serveur.serveurjeux.informationClient;

public class InformationServeur {

    //       ================================
    //       = Information pour  le serveur =
    //       ================================
    private int idJoueur=1;
    private String ipServer="0.0.0.0";
    private String ipClient="0.0.0.0";
    private int portServer=0;

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public String getIpClient() {
        return ipClient;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public int getPortServer() {
        return portServer;
    }

    public void setPortServer(int portServer) {
        this.portServer = portServer;
    }
}
