package serveur.serveurjeux.Controller;

import serveur.serveurjeux.DTO.Reception;
import serveur.serveurjeux.DTO.typeReponse.ReponseDeplacement;
import serveur.serveurjeux.DTO.typeReponse.ReponseInformationJoueurProche;
import serveur.serveurjeux.DTO.typeReponse.ReponseInformationMonstre;
import serveur.serveurjeux.DTO.typeReponse.ReponseNettoyageAffichage;
import serveur.serveurjeux.Entity.Case;
import serveur.serveurjeux.Entity.Client;
import serveur.serveurjeux.Entity.MapCase;
import serveur.serveurjeux.Entity.MapChunk;
import serveur.serveurjeux.Entity.NPC.Monstre;
import serveur.serveurjeux.Entity.Utility.CaseChemin;
import serveur.serveurjeux.Entity.Utility.Pile;
import serveur.serveurjeux.Entity.Utility.Position;

import java.awt.desktop.SystemEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deplacement {
    public static float delta = 0.016F;
    public static final int TICMAX = 1000;

    public static void deplacerPersonnage(Client client, int orientation) throws IOException {
        float posInitX = client.personnage.getEntite().getX();
        float posInitY = client.personnage.getEntite().getY();

        float deplacement;
        double v = Math.log(client.personnage.getEntite().getVitesse()) + 1;
        switch (orientation) {
            case 0:
                posInitY -= (float) ((client.personnage.getEntite().getVitesse() * delta) * v);
                break;
            case 1:
                deplacement = client.personnage.getEntite().getVitesse() * delta;
                deplacement = (float) (deplacement * Math.sqrt(2));
                posInitX -= (float) (deplacement * v);
                posInitY -= (float) (deplacement * v);
                break;
            case 2:
                posInitX -= (float) ((client.personnage.getEntite().getVitesse() * delta) * v);
                break;
            case 3:
                deplacement = client.personnage.getEntite().getVitesse() * delta;
                deplacement = (float) (deplacement * Math.sqrt(2));
                posInitX -= (float) (deplacement * v);
                posInitY += (float) (deplacement * v);
                break;
            case 4:
                posInitY += (float) ((client.personnage.getEntite().getVitesse() * delta) * v);
                break;
            case 5:
                deplacement = client.personnage.getEntite().getVitesse() * delta;
                deplacement = (float) (deplacement * Math.sqrt(2));
                posInitX += (float) (deplacement * v);
                posInitY += (float) (deplacement * v);
                break;
            case 6:
                posInitX += (float) ((client.personnage.getEntite().getVitesse() * delta) * v);
                break;
            case 7:
                deplacement = client.personnage.getEntite().getVitesse() * delta;
                deplacement = (float) (deplacement * Math.sqrt(2));
                posInitX += (float) (deplacement * v);
                posInitY -= (float) (deplacement * v);
                break;
            default:
                System.out.println("deplacement erreur: " + orientation);
                break;
        }

        if (posInitX < 0 || posInitX > MapCase.cases.get(0).size() - 1 || posInitY < 0 || posInitY > MapCase.cases.size() - 1) {
            return;
        }

        float[][] pointsCollision = {
                {posInitX, posInitY, 1},
                {posInitX + 0.16f, posInitY, 2},
                {posInitX, posInitY - 0.16f, 3},
                {posInitX - 0.16f, posInitY, 4}
        };

        for (float[] point : pointsCollision) {
            int checkX = (int) Math.floor(point[0]);
            int checkY = (int) Math.floor(point[1]);
            if (checkY < 0 || checkY >= MapCase.cases.size() || checkX < 0 || checkX >= MapCase.cases.get(checkY).size()) {
                return;
            }

            Case caseVerif = MapCase.cases.get(checkY).get(checkX);

            if (caseVerif != null && (caseVerif.symbole == '0' || caseVerif.symbole == '3' || caseVerif.symbole == '2' || caseVerif.symbole == '4')) {
                switch (caseVerif.symbole) {
                    case '0':
                        return;
                    case '2':
                        return;
                    case '3':
                        return;
                    case '4':
                        return;
                    default:
                        break;
                }
            }
        }

        int caseX = (int) (client.personnage.getEntite().getX());
        int caseY = (int) (client.personnage.getEntite().getY());


        int chunkX = (int) (posInitX / MapChunk.tailleChunks);
        int chunkY = (int) (posInitY / MapChunk.tailleChunks);
        if (client.personnage.getEntite().getyMatrice() / MapChunk.tailleChunks != (int) posInitY / MapChunk.tailleChunks) {
            MapChunk.mapChunks.get(client.personnage.getEntite().getyMatrice() / MapChunk.tailleChunks).get(client.personnage.getEntite().getxMatrice() / MapChunk.tailleChunks).clients.remove(client);
            MapChunk.mapChunks.get(chunkY).get(chunkX).clients.add(client);
        } else if (client.personnage.getEntite().getxMatrice() / MapChunk.tailleChunks != (int) posInitX / MapChunk.tailleChunks) {
            MapChunk.mapChunks.get(client.personnage.getEntite().getyMatrice() / MapChunk.tailleChunks).get(client.personnage.getEntite().getxMatrice() / MapChunk.tailleChunks).clients.remove(client);
            MapChunk.mapChunks.get(chunkY).get(chunkX).clients.add(client);
        }

        if((int) posInitY != client.personnage.getEntite().getyMatrice()){
            MapCase.cases.get(client.personnage.getEntite().getyMatrice()).get(client.personnage.getEntite().getxMatrice()).clients.remove(client);
            MapCase.cases.get((int) posInitY).get((int) posInitX).clients.add(client);
            client.personnage.getEntite().setxMatrice((int) posInitX);
            client.personnage.getEntite().setyMatrice((int) posInitY);
        } else if((int) posInitX != client.personnage.getEntite().getxMatrice()){
            MapCase.cases.get(client.personnage.getEntite().getyMatrice()).get(client.personnage.getEntite().getxMatrice()).clients.remove(client);
            MapCase.cases.get((int) posInitY).get((int) posInitX).clients.add(client);
            client.personnage.getEntite().setxMatrice((int) posInitX);
            client.personnage.getEntite().setyMatrice((int) posInitY);
        }

        client.personnage.getEntite().setX(posInitX);
        client.personnage.getEntite().setY(posInitY);

    }

    // fonction de déplacement pour les monstres
    public static void deplacerMonstre(Monstre monstre, float cibleX, float cibleY) throws IOException {
        float posInitX = monstre.getX();
        float posInitY = monstre.getY();

        //Calcule du vecteur entre le monstre est la cible
        float vecteurX = cibleX - posInitX;
        float vecteurY = cibleY - posInitY;

        //Normalisation du vecteur entre le monstre et la cible
        //calcule de la longueur du vecteur
        float longueur = (float) Math.sqrt(vecteurX * vecteurX + vecteurY * vecteurY);
        if (longueur != 0) { // on vérifie si le vecteur n'est pas nul (0,0)
            vecteurX = vecteurX / longueur;
            vecteurY = vecteurY / longueur;
        } else {
            vecteurX = posInitX;
            vecteurY = posInitY;
        }

        float deplacement;
        double v = Math.log(monstre.getVitesse()) + 1;

        //multiplication par la vitesse du monstre
        vecteurX = (float) (vecteurX * monstre.getVitesse() * delta * v);
        vecteurY = (float) (vecteurY * monstre.getVitesse() * delta * v);


        posInitX += vecteurX;
        posInitY += vecteurY;

        int chunkX = (int) (posInitX / MapChunk.tailleChunks);
        int chunkY = (int) (posInitY / MapChunk.tailleChunks);
        if (monstre.getYMatrice() / MapChunk.tailleChunks != (int) posInitY / MapChunk.tailleChunks) {
            MapChunk.mapChunks.get(monstre.getYMatrice() / MapChunk.tailleChunks).get(monstre.getXMatrice() / MapChunk.tailleChunks).monstres.remove(monstre);
            MapChunk.mapChunks.get(chunkY).get(chunkX).monstres.add(monstre);
        } else if (monstre.getXMatrice() / MapChunk.tailleChunks != (int) posInitX / MapChunk.tailleChunks) {
            MapChunk.mapChunks.get(monstre.getYMatrice() / MapChunk.tailleChunks).get(monstre.getXMatrice() / MapChunk.tailleChunks).monstres.remove(monstre);
            MapChunk.mapChunks.get(chunkY).get(chunkX).monstres.add(monstre);
        }


        if ((int) posInitY != (int) monstre.getY()) {
            MapCase.cases.get((int) monstre.getY()).get((int) monstre.getX()).monstres.remove(monstre);
            MapCase.cases.get((int) monstre.getY()).get((int) monstre.getX()).monstres.add(monstre);
            monstre.setXMatrice((int) posInitX);
            monstre.setYMatrice((int) posInitY);
        } else if ((int) posInitX != (int) monstre.getX()) {
            MapCase.cases.get((int) monstre.getY()).get((int) monstre.getX()).monstres.remove(monstre);
            MapCase.cases.get((int) monstre.getY()).get((int) monstre.getX()).monstres.add(monstre);
            monstre.setXMatrice((int) posInitX);
            monstre.setYMatrice((int) posInitY);
        }

        monstre.setX(posInitX);
        monstre.setY(posInitY);


////        Reception reception = new Reception();
////        ReponseDeplacement reponseDeplacement = new ReponseDeplacement();
////        reponseDeplacement.direction = orientation;
////        reponseDeplacement.x = client.personnage.getEntite().getX();
////        reponseDeplacement.y = client.personnage.getEntite().getY();
////        reception.ajouterMessage(reponseDeplacement);
////
////        client.writer.writeObject(reception);
////        client.writer.flush();

    }

    public static void envoieInformationDeplacementJoueurProche(Client client, int orientation) throws IOException {
        for (int y = (client.personnage.getEntite().getyMatrice() / 32) - 10; y <= (client.personnage.getEntite().getyMatrice() / 32) + 10; y++) {
            for (int x = (client.personnage.getEntite().getxMatrice() / 32) - 10; x <= (client.personnage.getEntite().getxMatrice() / 32) + 10; x++) {
                if (x >= 0 && y >= 0 && x < (MapChunk.mapChunks.get(0).size() - 1) && y < MapChunk.mapChunks.get(MapChunk.mapChunks.size() - 1).size()) {
                    for (Client joueurProche : MapChunk.mapChunks.get(y).get(x).clients) {
                        if (joueurProche != client) {
                            // remettre si on a un moyen côter client de gérer  joueur par joueur
//                            Reception reception = new Reception();
//                            ReponseDeplacementAutreJoueur reponseDeplacementAutreJoueur = new ReponseDeplacementAutreJoueur();
//                            reponseDeplacementAutreJoueur.direction = orientation;
//                            reponseDeplacementAutreJoueur.personnage = client.personnage;
//                            reception.ajouterMessage(reponseDeplacementAutreJoueur);
//
//                            joueurProche.writer.writeObject(reception);
//                            joueurProche.writer.flush();
                        }
                        envoieInformationJoueurProche(joueurProche);

                    }
                }
            }
        }
    }

    public static void envoieInformationJoueurProche(Client client) throws IOException {
        if (client.personnage != null) {
            int nbPersonne = 0;
            Reception reception = new Reception();
            reception.ajouterMessage(new ReponseNettoyageAffichage());
            for (int y = (client.personnage.getEntite().getyMatrice() / 32) - 10; y <= (client.personnage.getEntite().getyMatrice() / 32) + 10; y++) {
                for (int x = (client.personnage.getEntite().getxMatrice() / 32) - 10; x <= (client.personnage.getEntite().getxMatrice() / 32) + 10; x++) {
                    if (x >= 0 && y >= 0 && x < (MapChunk.mapChunks.get(0).size() - 1) && y < MapChunk.mapChunks.get(MapChunk.mapChunks.size() - 1).size()) {
                        for (Client joueurProche : MapChunk.mapChunks.get(y).get(x).clients) {
                            if (joueurProche != client) {
                                nbPersonne++;
                                ReponseInformationJoueurProche reponseInformationJoueurProche = new ReponseInformationJoueurProche();
                                reponseInformationJoueurProche.personnage = joueurProche.personnage;
                                reception.ajouterMessage(reponseInformationJoueurProche);
                            }
                        }
                    }
                }
            }
            if (nbPersonne != 0) {
                client.writer.writeObject(reception);
                client.writer.flush();
                client.writer.reset();
            }
        }
    }

    //Envoie la position des monstres proches du client
    public static void envoieInformationMonstreProche(Client client) throws IOException {
        if (client.personnage != null) {
            int nbMonstre = 0;
            Reception reception = new Reception();
            reception.ajouterMessage(new ReponseNettoyageAffichage());
            for (int y = (client.personnage.getEntite().getyMatrice() / 32) - 10; y <= (client.personnage.getEntite().getyMatrice() / 32) + 10; y++) {
                for (int x = (client.personnage.getEntite().getxMatrice() / 32) - 10; x <= (client.personnage.getEntite().getxMatrice() / 32) + 10; x++) {
                    if (x >= 0 && y >= 0 && x < (MapChunk.mapChunks.get(0).size() - 1) && y < MapChunk.mapChunks.get(MapChunk.mapChunks.size() - 1).size()) {
                        for (Monstre monstreProche : MapChunk.mapChunks.get(y).get(x).monstres) {
                            nbMonstre++;
                            ReponseInformationMonstre reponseInformationMonstre = new ReponseInformationMonstre();
                            reponseInformationMonstre.typeMonstre = monstreProche.getType();
                            reponseInformationMonstre.x = monstreProche.getX();
                            reponseInformationMonstre.y = monstreProche.getY();
                            reception.ajouterMessage(reponseInformationMonstre);
                        }
                    }
                }
            }
            if (nbMonstre != 0) {
                client.writer.writeObject(reception);
                client.writer.flush();
                client.writer.reset();
            }
        }
    }

    // fonction de recherche de chemin pour atteindre la cible
    public static List<Position> rechercheChemin(float x1, float y1, float x2, float y2) throws IOException {
        boolean nonBloque = false;
        boolean precNonBloque = false;
        List<Position> chemin = new ArrayList<>(); // Liste ayant les cases pour former le chemin entre (x1,y1)(x2,y2) ((x1,y1) exclu)
        List<CaseChemin> visites = new ArrayList<>(); // Liste des positions visitées
        List<CaseChemin> nonVisites = new ArrayList<>(); // Liste des positions non-visitées
        CaseChemin caseActuelle = null;
        int pere = -1;
        int nbBoucle = 0;

//        System.out.println("Emission d'un rayon...");
        nonBloque = emissionRayon(x1, y1, x2, y2);

        if (nonBloque) {
//            System.out.println("Rayon non-bloqué");
            chemin.add(new Position(x2, y2));
            return chemin;
        } else {
//            System.out.println("Rayon bloqué ... recherche d'un autre chemin");
//            return null;
            caseActuelle = new CaseChemin(new Position(x1, y1), -1, -1, 0);
            visites.add(caseActuelle);

            for(CaseChemin caseChemin : casesVoisines(caseActuelle, visites, x2, y2)){
                if(!nonVisites.contains(caseChemin) && !visites.contains(caseChemin)){ //on prend en compte cette case seulement si on ne la pas déjà rencontrée
                    nonVisites.add(caseChemin);
                }
            }

            while(!nonBloque && !nonVisites.isEmpty() && nbBoucle < TICMAX){
                caseActuelle = min(nonVisites);

//                System.out.println("caseActuelle :" + caseActuelle.getPosition() + " pere:" + caseActuelle.getPere() + " prec:" + caseActuelle.getPrec() + " cout:" + caseActuelle.getCout());

                // on passe la nouvelle case dans la liste des cases visitées
                nonVisites.remove(caseActuelle);
                visites.add(caseActuelle);

                precNonBloque = emissionRayon(caseActuelle.getPosition().getX(), caseActuelle.getPosition().getY(), visites.get(caseActuelle.getPere()).getPosition().getX(), visites.get(caseActuelle.getPere()).getPosition().getY());
                if(!precNonBloque){ // Si le rayon est bloqué, il faut changer de père
                    caseActuelle.setPere(caseActuelle.getPrec());
//                    visites.get(visites.size()-1).setPere(caseActuelle.getPrec()); // caseActuelle est le dernier qu'on a mis
                }

                // on ajoute les voisins de la case prise APRES, sinon le père n'est pas mise à jour
                for(CaseChemin caseChemin : casesVoisines(caseActuelle, visites, x2, y2)){
                    if(!nonVisites.contains(caseChemin) && !visites.contains(caseChemin)){ //on prend en compte cette case seulement si on ne la pas déjà rencontrée
                        nonVisites.add(caseChemin);
                    }
                }

                nonBloque = emissionRayon(caseActuelle.getPosition().getX(), caseActuelle.getPosition().getY(), x2, y2);
                nbBoucle++;
            }

            if(nbBoucle < TICMAX) { // on a trouvé un chemin à temps
                chemin.add(new Position(x2, y2));
                pere = findCase(caseActuelle, visites);
//                pere = caseActuelle.getPere();

                while (pere != -1) {
                    chemin.add(caseActuelle.getPosition());
                    caseActuelle = visites.get(caseActuelle.getPere());
                    pere = caseActuelle.getPere();
                }

                Collections.reverse(chemin);// On doit inverser la liste
                return chemin;
            } else {
//                System.out.println("Pas de chemin trouver à temps");
                return null;
            }
        }
    }


    // fonction qui va emettre un rayon entre deux points pour voir s'il est bloqué par un obstacle
    // inspiré de l'algo de Bresenham version flottant
    // false : rayon bloqué | true : rayon non bloqué
    // ATTENTION : coordonnées négatives non-autorisées
    public static boolean emissionRayon(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        int n = Math.max( (int)(Math.abs(Math.floor(dx))), (int)(Math.abs(Math.floor(dy))));

//        System.out.println("Emission de " + x1 + "," + y1 + " et " + x2 + "," + y2);

        if (n == 0) {
//            System.out.println("n==0");
            return true;
        }

        dx = dx / n;
        dy = dy / n;

        int compteur = 0;
        //Réduction de dx et dy si nécessaire
        while(Math.abs(dx) > 1 && compteur < 100){
            dx = dx/2;
            compteur++;
        }
        compteur = 0;
        while(Math.abs(dy) > 1 && compteur < 100){
            dy = dy/2;
            compteur++;
        }

        float newX;
        float newY;

        float dx2, dy2;
        float tx, ty;

        int sx = (x1 < x2) ? 1 : -1; //Sens du mouvement
        int sy = (y1 < y2) ? 1 : -1; //idem

        boolean arretX = false;
        boolean arretY = false;
        boolean modifX = false;
        boolean modifY = false;


//        System.out.println("dx = " + dx + " dy = " + dy);
//        System.out.println("On check la case :" + Math.floor(x1) + "," + Math.floor(y1));
        if (isObstacle(MapCase.cases.get((int) Math.floor(y1)).get((int) Math.floor(x1)).symbole - '0')) { //Si la case est un obstacle, le rayon est bloqué
//            System.out.println("obstable : " + ((int) Math.floor(x1)) + "," + ((int) Math.floor(y1)));
            return false;
        }

        for (int i = 0; i < n; i++) {

//            System.out.println("coord debut : " + x1 + "," + y1);

            if (sx == 1 && x1 >= x2) {
                arretX = true;
//                System.out.println("arretX : x1 >= x2 :" + x1 + ">=" + x2);
            } else if (sx == -1 && x2 >= x1) {
//                System.out.println("arretX : x2 >= x1 :" + x2 + ">=" + x1);
                arretX = true;
            }

            if (sy == 1 && y1 >= y2) {
                arretY = true;
//                System.out.println("arretY : y1 >= y2 :" + y1 + ">=" + y2);
            } else if (sy == -1 && y2 >= y1) {
                arretY = true;
//                System.out.println("arretY : y2 >= y1 :" + y2 + ">=" + y1);
            }

            if (arretX && arretY) {
                return true;
            }

            if(x1 == x2 && y1 == y2){
                return true;
            }

            newX = x1 + dx;
            newY = y1 + dy;

            if(Math.floor(x1) != Math.floor(newX))
                modifX = true;
            if(Math.floor(y1) != Math.floor(newY))
                modifY = true;

//            System.out.println("coord fin : " + newX + "," + newY);

            if(modifX && modifY) {
                dx2 = Math.abs(x2 - x1);
                dy2 = Math.abs(y2 - y1);

                if (x1 < newX) {
                    tx = ((int) Math.ceil(x1) - x1) / dx2; //le temps qu'on va mettre pour aller dans la case x+1, y
                } else {
                    tx = (x1 - (int) Math.floor(x1)) / dx2; //le temps qu'on va mettre pour aller dans la case x+1, y
                }

                if (y1 < newY) {
                    ty = ((int) Math.ceil(y1) - y1) / dy2; //le temps qu'on va mettre pour aller dans la case x, y+1
                } else {
                    ty = (y1 - (int) Math.floor(y1)) / dy2; //le temps qu'on va mettre pour aller dans la case x, y+1
                }

//                System.out.println("tx = " + tx + " ty = " + ty);

                if (tx < ty) { //on passe d'abords par la case x+1,y
//                    System.out.println("On check la case :" + Math.floor(newX) + "," + Math.floor(y1));
                    if (isObstacle(MapCase.cases.get((int) Math.floor(y1)).get((int) Math.floor(newX)).symbole - '0')) { //Si la case est un obstacle, le rayon est bloqué
//                    System.out.println("obstable : " + ((int) Math.floor(newX)) + "," + ((int) Math.floor(y1)));
                        return false;
                    }
                } else if (ty < tx) { //on passe d'abords par la case x,y+1
//                    System.out.println("On check la case :" + Math.floor(x1) + "," + Math.floor(newY));
                    if (isObstacle(MapCase.cases.get((int) Math.floor(newY)).get((int) Math.floor(x1)).symbole - '0')) { //Si la case est un obstacle, le rayon est bloqué
//                    System.out.println("obstable : " + ((int) Math.floor(x1)) + "," + ((int) Math.floor(newY)));
                        return false;
                    }
                } else if (ty == tx) { // on verifie les deux cases
//                    System.out.println("On check la case :" + Math.floor(x1) + "," + Math.floor(newY) + "...");
//                    System.out.println("puis la case :" + Math.floor(newX) + "," + Math.floor(y1));
                    if (isObstacle(MapCase.cases.get((int) Math.floor(newY)).get((int) Math.floor(x1)).symbole - '0') ||
                            isObstacle(MapCase.cases.get((int) Math.floor(y1)).get((int) Math.floor(newX)).symbole - '0')
                    ) { //Si la case est un obstacle, le rayon est bloqué
//                    System.out.println("soit obstable : " + ((int) Math.floor(newX)) + "," + ((int) Math.floor(y1)));
//                    System.out.println("soit obstable : " + ((int) Math.floor(x1)) + "," + ((int) Math.floor(newY)));
                        return false;
                    }
                }
            }

            if ((int) Math.floor(newX) < 0 || (int) Math.floor(newY) < 0 || (int) Math.floor(newX) >= MapCase.cases.get(0).size() || (int) Math.floor(newY) >= MapCase.cases.size()) {
                System.out.println("hors map : " + ((int) Math.floor(newX)) + " " + ((int) Math.floor(newY)));
                return false;
            }

//            System.out.println("On check la case :" + Math.floor(newX) + "," + Math.floor(newY));
            if (isObstacle(MapCase.cases.get((int) Math.floor(newY)).get((int) Math.floor(newX)).symbole - '0')) { //Si la case est un obstacle, le rayon est bloqué
//                System.out.println("obstable : " + ((int) Math.floor(newX)) + "," + ((int) Math.floor(newY)));
                return false;
            }
            x1 = newX;
            y1 = newY;
        }

//        System.out.println("Arrivé en "  + x1 + "," + y1 + " sur " + x2 + "," + y2);
        return true;
    }

    // Renvoie la liste des cases voisines qui ne sont pas des obstacles
    public static List<CaseChemin> casesVoisines(CaseChemin caseChemin, List<CaseChemin> visite, float xDest, float yDest) {
        List<CaseChemin> voisins = new ArrayList<>();
        int caseX = (int)Math.floor(caseChemin.getPosition().getX());
        int caseY = (int)Math.floor(caseChemin.getPosition().getY());
        Position voisin;
        int pere = caseChemin.getPere();

        if(pere == -1){ //père unique à la première case
            pere = 0;
        }

        if(caseX != 0){ //case gauche
            if(!isObstacle(MapCase.cases.get(caseY).get(caseX - 1).symbole-'0')){ //si ce n'est pas un obstacle
                voisin = new Position((float)(caseX-1+0.5), (float)(caseY+0.5));
                voisins.add(new CaseChemin(voisin, pere, findCase(caseChemin, visite), couts(voisin, caseChemin.getCout()+1, xDest, yDest)));
            }
        }
        if(caseY != 0){ //case haute
            if(!isObstacle(MapCase.cases.get(caseY - 1).get(caseX).symbole-'0')){ //si ce n'est pas un obstacle
                voisin = new Position((float)(caseX+0.5), (float)(caseY-1+0.5));
                voisins.add(new CaseChemin(voisin, pere, findCase(caseChemin, visite), couts(voisin, caseChemin.getCout()+1, xDest, yDest)));
            }
        }
        if(caseX != MapCase.cases.get(0).size()-1){ //case droite
            if(!isObstacle(MapCase.cases.get(caseY).get(caseX + 1).symbole-'0')){ //si ce n'est pas un obstacle
                voisin = new Position((float)(caseX+1.5), (float)(caseY+0.5));
                voisins.add(new CaseChemin(voisin, pere, findCase(caseChemin, visite), couts(voisin, caseChemin.getCout()+1, xDest, yDest)));
            }
        }
        if(caseY != MapCase.cases.size()){ //case basse
            if(!isObstacle(MapCase.cases.get(caseY + 1).get(caseX).symbole-'0')){ //si ce n'est pas un obstacle
                voisin = new Position((float)(caseX+0.5), (float)(caseY+1.5));
                voisins.add(new CaseChemin(voisin, pere, findCase(caseChemin, visite), couts(voisin, caseChemin.getCout()+1, xDest, yDest)));
            }
        }

        return  voisins;
    }

    // renvoie l'indice de la case qui se trouve dans la liste
    public static int findCase(CaseChemin caseChemin, List<CaseChemin> liste){
        int i;

        for(i = 0; i < liste.size(); i++){
            if(liste.get(i).equals(caseChemin)){
                return i;
            }
        }
        System.out.println("Case pas dans la liste");
        return -1;
    }

    // Calcule le cout de déplacement d'une case, en fonction de la destination
    public static int couts(Position pos, int coutReel, float xDest, float yDest){
        int cout;
        int caseX = (int)Math.floor(xDest);
        int caseY = (int)Math.floor(yDest);

        cout = coutReel; // cout de déplacement déja parcourue

        cout += (int) (Math.abs(caseX-pos.getX()) + Math.abs(caseY-pos.getY()));

        return cout;
    }

    // renvoie la case avec le cout minimum de la liste
    public static CaseChemin min(List<CaseChemin> liste){
        CaseChemin caseCheminMin = null;

        for(CaseChemin caseChemin : liste){
            if(caseCheminMin == null){
                caseCheminMin = caseChemin;
            } else {
                if(caseCheminMin.getCout() > caseChemin.getCout()){ // on récupère celui qui à le moins de cout
                    caseCheminMin = caseChemin;
                }
            }
        }

        return caseCheminMin;
    }

    //Dit si une case est un obstacle ou non
    public static boolean isObstacle(int code){
        if(code == MapCase.ARBRE ||
        code == MapCase.ROCHER ||
        code == MapCase.EAU ||
        code == MapCase.LAC) {
            return true;
        }
        return false;
    }

}
