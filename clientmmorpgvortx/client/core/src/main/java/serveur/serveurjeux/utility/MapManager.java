package serveur.serveurjeux.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;
import java.awt.Point;
import java.util.HashMap;
// Cette classe à pour unique but de gerer les différentes images de la/des carte(s)

public class MapManager {

//    // Charge le fichier JSON contenant les images de la carte et leurs coordonnées
//    public static Map<String, Point> chargerJSON(String chemin) throws Exception {
//        Map<String, Point> carteDonnees = new HashMap<>(); //C'est ici qu'on va stocker les images de la carte avec leurs coordonnées
//        ObjectMapper mapper = new ObjectMapper();
//
//        // Lecture dynamique en Map<String, Map<String, Integer>>
//        Map<String, Map<String, Integer>> donnee = mapper.readValue(new File(chemin), Map.class);
//
//        // Convertir en Map<String, Point>
//        for (Map.Entry<String, Map<String, Integer>> entry : donnee.entrySet()) {
//            String imageNom = entry.getKey();
//            Map<String, Integer> coords = entry.getValue();
//            int x = coords.get("x");
//            int y = coords.get("y");
//            carteDonnees.put(imageNom, new Point(x, y)); // on met le nom de l'image avec ces coordonnées
//        }
//
//        return carteDonnees;
//    }

    // Charge le JSON et retourne les données du JSON
    public static Map<String, Object> chargerJSON(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), Map.class);
    }

    // Retourne la hauteur de la carte informée dans le JSON
    public static int hauteurJSON(Map<String, Object> json) {
        return (int) json.get("hauteur");
    }

    // Retourne la largeur de la carte informée dans le JSON
    public static int largeurJSON(Map<String, Object> json) {
        return (int) json.get("largeur");
    }

    // Retourne les données des toutes les images dans le Json
    public  static Map<String, Map<String, Integer>> imagesJson(Map<String, Object> json){
        return (Map<String, Map<String, Integer>>) json.get("images");
    }

    // Methode pour tester la classe utilitaire
//    public static void main(String[] args) {
//        try {
//            // Chargement des données JSON
//            Map<String, Object> donnees = chargerJSON("assets/map/main.json");
//
//            // Extraction des valeurs du JSON
//            int largeur = largeurJSON(donnees);
//            int hauteur = hauteurJSON(donnees);
//
//            // Récupération des images et de leurs coordonnées
//            Map<String, Point> imageDonnees = new HashMap<>();
//            Map<String, Map<String, Integer>> images = imagesJson(donnees);
//
//            for (Map.Entry<String, Map<String, Integer>> image : images.entrySet()) {
//                String imageNom = image.getKey();
//                Map<String, Integer> coords = image.getValue();
//                int x = coords.get("x");
//                int y = coords.get("y");
//                imageDonnees.put(imageNom, new Point(x, y));
//            }
//
//            // Affichage des valeurs récupérées
//            System.out.println("Largeur : " + largeur);
//            System.out.println("Hauteur : " + hauteur);
//
//            // Affichage des images et de leurs coordonnées
//            for (Map.Entry<String, Point> image : imageDonnees.entrySet()) {
//                System.out.println("Image: " + image.getKey() + " -> Coordonnées: " + image.getValue());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}

