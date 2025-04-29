package serveur.serveurjeux.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapCase {
    public static final int VIDE = -1;
    public static final int EAU = 0;
    public static final int PLAINE = 1;
    public static final int SABLE = 5;
    public static final int ROCHER = 2;
    public static final int ARBRE = 3;
    public static final int LAC = 4;
    public static final int NEIGE = 6;

    public static ArrayList<ArrayList<Case>> cases = new ArrayList<>();

    public static void construireMap() {
        try (InputStream inputStream = MapCase.class.getResourceAsStream("/main.txt");
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            cases.clear();
            ArrayList<Case> ligneActuelle = new ArrayList<>();
            int x = 0, y = 0;

            int caractere;
            while ((caractere = reader.read()) != -1) {
                if (caractere == '\n') {
                    cases.add(ligneActuelle);
                    ligneActuelle = new ArrayList<>();
                    x = 0;
                    y++;
                } else if (caractere != '\r') {
                    Case nouvelleCase = new Case(x, y, (char) caractere);
                    ligneActuelle.add(nouvelleCase);
                   // System.out.println("Case créée : (" + x + ", " + y + ") avec symbole : " + (char) caractere);

                    x++;
                }
            }

            if (!ligneActuelle.isEmpty()) {
                cases.add(ligneActuelle);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

