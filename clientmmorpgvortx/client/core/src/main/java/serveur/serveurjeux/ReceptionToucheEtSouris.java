package serveur.serveurjeux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.typeMessage.DemandeCaseSelection;
import serveur.serveurjeux.DTO.typeMessage.DemandeDeplacement;
import serveur.serveurjeux.DTO.typeMessage.DemandeLogin;
import serveur.serveurjeux.informationClient.Reseau;

import java.io.IOException;
import java.io.PrintWriter;

public class ReceptionToucheEtSouris {

    //--------------------------------------------------------//
    //
    //        Fonction renvoyant les touches pressées
    //
    //--------------------------------------------------------//
    public static void receptionToucheAppuyer() throws IOException {
        if (Reseau.socket == null) {
            return; // Sort de la méthode si le socket est null
        }


        // Détection des touches pressées
        boolean z = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean q = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean s = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean d = Gdx.input.isKeyPressed(Input.Keys.D);

        // Vérification de chaque cas
        if (z && !q && !s && !d) {
            System.out.println("Z");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=0;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
        }
        else if (!z && q && !s && !d) {
            System.out.println("Q");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=2;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();

        }
        else if (!z && !q && s && !d) {
            System.out.println("S");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=4;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
        }
        else if (!z && !q && !s && d) {
            System.out.println("D");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=6;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
        }
        else if (z && q && !s && !d) {
            System.out.println("ZQ");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=1;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
        }
        else if (z && d && !s && !q) {
            System.out.println("ZD");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=7;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
        }
        else if (s && q && !z && !d) {
            System.out.println("SQ");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=3;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
        }
        else if (s && d && !z && !q) {
            System.out.println("SD");
            Envoie envoie = new Envoie(Reseau.address,Reseau.pseudo,Reseau.UUID);
            DemandeDeplacement demandeDeplacement = new DemandeDeplacement();
            demandeDeplacement.direction=5;
            envoie.ajouterMessage(demandeDeplacement);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
        }

    }








    //--------------------------------------------------------//
    //
    //    Fonction renvoyant la position de la souris
    //    lors d un clic gauche ou droit
    //    Si besoin elle est faite
    //
    //--------------------------------------------------------//
    public static void receptionClicSouris() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inversion de l'axe Y
            System.out.println("(Clique gauche, " + mouseX + ", " + mouseY + ")");
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inversion de l'axe Y
            System.out.println("(Clique droit, " + mouseX + ", " + mouseY + ")");
        }
    }

    //--------------------------------------------------------//
    //
    //    Fonction renvoyant la position de la souris
    //    et le vecteur de direction par rapport au centre de
    //              de la fenetres
    //
    //--------------------------------------------------------//
//    public static void receptionClicSourisAvecDirection() {
//        // le milieu de la fenetre
//        int centerX = Gdx.graphics.getWidth() / 2;
//        int centerY = Gdx.graphics.getHeight() / 2;
//
//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//            int mouseX = Gdx.input.getX();
//            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inversion de l'axe Y
//
//            // vecteur de la direction de la souris
//            Vector2 direction = new Vector2(mouseX - centerX, mouseY - centerY);
//
//            System.out.println("(Clique gauche, " + mouseX + ", " + mouseY + ")");
//            System.out.println("Vecteur direction : " + direction);
//        }
//
//        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
//            int mouseX = Gdx.input.getX();
//            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inversion de l'axe Y
//
//            // vecteur de la direction de la souris
//            Vector2 direction = new Vector2(mouseX - centerX, mouseY - centerY);
//
//            System.out.println("(Clique droit, " + mouseX + ", " + mouseY + ")");
//            System.out.println("Vecteur direction : " + direction);
//        }
//    }


    public static void receptionClicSourisAvecDirection() throws IOException {
        // Centre de l’écran
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            // Position de la souris
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inversion de l’axe Y

            // Calcul du déplacement relatif
            float dx = mouseX - centerX;
            float dy = centerY - mouseY; // Y inversé pour respecter LibGDX

            // Calcul de l’angle en degrés
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

            // Normalisation de l’angle pour être entre 0 et 360°
            if (angle < 0) {
                angle += 360;
            }

            // Détermination de la direction discrète (0 à 7) en fonction des plages d’angle
            int direction;
            if (angle >= 337.5 || angle < 22.5) {
                direction = 2;  // Droite
            } else if (angle >= 22.5 && angle < 67.5) {
                direction = 3;  // Bas-Droite
            } else if (angle >= 67.5 && angle < 112.5) {
                direction = 4;  // Bas
            } else if (angle >= 112.5 && angle < 157.5) {
                direction = 5;  // Bas-Gauche
            } else if (angle >= 157.5 && angle < 202.5) {
                direction = 6;  // Gauche
            } else if (angle >= 202.5 && angle < 247.5) {
                direction = 7;  // Haut-Gauche
            } else if (angle >= 247.5 && angle < 292.5) {
                direction = 0;  // Haut
            } else { // (angle >= 292.5 && angle < 337.5)
                direction = 1;  // Haut-Droite
            }
            Envoie envoie = new Envoie(Reseau.ip, Reseau.pseudo, Reseau.UUID);
            DemandeCaseSelection demandeCaseSelection = new DemandeCaseSelection();
            demandeCaseSelection.direction = direction;
            demandeCaseSelection.indexX = Inventaire.CaseInventaireSelectionnerY;
            demandeCaseSelection.indexY = Inventaire.CaseInventaireSelectionnerX;
            envoie.ajouterMessage(demandeCaseSelection);
            Reseau.writer.writeObject(envoie);
            Reseau.writer.flush();
            Reseau.writer.reset();

        }
    }




}
