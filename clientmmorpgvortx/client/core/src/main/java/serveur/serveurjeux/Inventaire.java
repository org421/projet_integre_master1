package serveur.serveurjeux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import serveur.serveurjeux.DTO.Client.caseInventaireDTOClient;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.typeMessage.DemandeCaseSelection;
import serveur.serveurjeux.DTO.typeMessage.DemandeChangementCaseInventaire;
import serveur.serveurjeux.Entity.PersonnageJoueur;
import serveur.serveurjeux.informationClient.Reseau;

import java.io.IOException;

public class Inventaire {
    private boolean visible = false;
    private final int largeur = 450;
    private final int hauteur = 230;
    private final int casesParLigne = 8;
    private final int casesParColonne = 4;
    private final int tailleCase = 50;
    private int x = (Gdx.graphics.getWidth() - largeur) / 2; // Centrer horizontalement
    private int y = (Gdx.graphics.getHeight() - hauteur) / 2; // Centrer verticalement

    private Texture fondInventaire;
    private Texture caseTexture;
    private BitmapFont font;

    private Texture caillouxTexture;
    private Texture epeeTexture;
    private Texture bottesTexture;
    private Texture casqueTexture;
    private Texture gantsTexture;
    private Texture plastronTexture;
    private Texture potionTexture;
    private Texture petiteAttaqueTexture;
    private Texture potionVitesseTexture;
    private Texture zoneDeDegatsTexture;
    private Texture zoneDeSoinTexture;
    private Texture rayonLaserTexture;
    private Texture bouleDeFeuTexture;

    public static int CaseInventaireSelectionnerX =0;
    public static int CaseInventaireSelectionnerY =0;




    public Inventaire() {
        fondInventaire = new Texture("ig/SimplePanel01.png");  // Crée un fond
        caseTexture = new Texture("ig/fond_inv.png");  // Crée une texture de case


        caillouxTexture = new Texture("items/cailloux.png");  // Chargement de l'image cailloux
        epeeTexture = new Texture("items/epee.png");  // Chargement de l'image cailloux
        bottesTexture = new Texture("items/bottes.png");  // Chargement de l'image cailloux
        casqueTexture = new Texture("items/casque.png");  // Chargement de l'image cailloux
        gantsTexture = new Texture("items/gant.png");  // Chargement de l'image cailloux
        plastronTexture = new Texture("items/armure.png");  // Chargement de l'image cailloux
        potionTexture = new Texture("items/potion.png");  // Chargement de l'image cailloux
        petiteAttaqueTexture = new Texture("competence/petiteattaque.png");
        potionVitesseTexture = new Texture("items/potionVitesse.png");
        zoneDeDegatsTexture = new Texture("competence/zonededegats.png");
        zoneDeSoinTexture = new Texture("competence/zonedesoin.png");
        bouleDeFeuTexture = new Texture("competence/bouledefeu.png");
        rayonLaserTexture = new Texture("competence/rayonlaser.png");
        font = new BitmapFont();
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;

        batch.draw(fondInventaire, x, y, largeur, hauteur);

        // Dessiner les cases
        for (int ligne = 0; ligne < casesParColonne; ligne++) {
            for (int col = 0; col < casesParLigne; col++) {
                int caseX = x + col * (tailleCase + 5);
                int caseY = y + hauteur - ((ligne + 1) * (tailleCase + 5));

                batch.draw(caseTexture, caseX, caseY, tailleCase, tailleCase);


                if (PersonnageJoueur.inventaire != null && PersonnageJoueur.inventaire.inventaireDTOClients[ligne][col] != null) {
                    caseInventaireDTOClient caseInv = PersonnageJoueur.inventaire.inventaireDTOClients[ligne][col];
                    if (caseInv.type == 0) {
                        switch (caseInv.typeObjet) {
                            case 1:
                                batch.draw(epeeTexture, caseX + 2, caseY + 2, 45, 45);

                                break;
                            case 2:
                                batch.draw(casqueTexture, caseX + 2, caseY + 2, 45, 45);

                                break;
                            case 3:
                                batch.draw(plastronTexture, caseX + 2, caseY + 2, 45, 45);

                                break;
                            case 4:
                                batch.draw(bottesTexture, caseX + 2, caseY + 2, 45, 45);

                                break;
                            case 5:
                                batch.draw(gantsTexture, caseX + 2, caseY + 2, 45, 45);

                                break;
                            case 6:
                                switch (caseInv.id) {
                                    case 1:
                                        System.out.print("cas 1 de la potion");
                                        batch.draw(potionTexture, caseX + 2, caseY + 2, 45, 45);
                                        break;
                                    case 2:
                                        System.out.print("cas 2 de la potion");
                                        batch.draw(potionVitesseTexture, caseX + 2, caseY + 2, 45, 45);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 7:
                                batch.draw(caillouxTexture, caseX + 2, caseY + 2, 45, 45);

                                break;
                            default:
                                break;
                        }
                    }
                    if (caseInv.type == 1) {
                        switch (caseInv.id) {
                            case 1:
                                batch.draw(petiteAttaqueTexture, caseX + 2, caseY + 2, 45, 45);

                                break;
                            case 2:
                                batch.draw(rayonLaserTexture, caseX + 2, caseY + 2, 45, 45);
                                break;
                            case 3:
                                batch.draw(bouleDeFeuTexture, caseX + 2, caseY + 2, 45, 45);
                                break;
                            case 4:
                                batch.draw(zoneDeDegatsTexture, caseX + 2, caseY + 2, 45, 45);
                                break;
                            case 5:
                                batch.draw(zoneDeSoinTexture, caseX + 2, caseY + 2, 45, 45);
                                break;
                            default:
                                break;
                        }

                    }

                    }





            }
        }
    }

    public void toggle() {
        visible = !visible;
    }

    public void handleClick(int screenX, int screenY) throws IOException {
        if (!visible) return;

        for (int ligne = 0; ligne < casesParColonne; ligne++) {
            for (int col = 0; col < casesParLigne; col++) {
                int caseX = x + col * (tailleCase + 5);
                int caseY = y + hauteur - ((ligne + 1) * (tailleCase + 5));

                Rectangle caseRect = new Rectangle(caseX, caseY, tailleCase, tailleCase);

                if (caseRect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                        // Stocke les indices X et Y de la case sélectionnée avec un clic droit
                        FirstScreen.CaseInventaireSelectionnerX = ligne;
                        FirstScreen.CaseInventaireSelectionnerY = col;
                        System.out.println("Sélection rapide (clic droit) : " + ligne + ", " + col);
                        CaseInventaireSelectionnerX = ligne;
                        CaseInventaireSelectionnerY = col;

                    } else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                        // Gestion du déplacement d'objet avec un clic gauche
                        if (FirstScreen.isFirstSelection) {
                            FirstScreen.CaseInventaireDepartX = ligne;
                            FirstScreen.CaseInventaireDepartY = col;
                            FirstScreen.isFirstSelection = false;
                            System.out.println("Première sélection: " + ligne + ", " + col);
                        } else {
                            FirstScreen.CaseInventaireFinX = ligne;
                            FirstScreen.CaseInventaireFinY = col;
                            FirstScreen.isFirstSelection = true;

                            System.out.println("Fin case " + FirstScreen.CaseInventaireFinX + "  " + FirstScreen.CaseInventaireFinY);
                            Envoie envoie = new Envoie(Reseau.ip, Reseau.pseudo, Reseau.UUID);
                            DemandeChangementCaseInventaire demandeChangementCaseInventaire = new DemandeChangementCaseInventaire();
                            demandeChangementCaseInventaire.indexXDepart = FirstScreen.CaseInventaireDepartY;
                            demandeChangementCaseInventaire.indexYDepart = FirstScreen.CaseInventaireDepartX;
                            demandeChangementCaseInventaire.indexXArrivee = FirstScreen.CaseInventaireFinY;
                            demandeChangementCaseInventaire.indexYArrivee = FirstScreen.CaseInventaireFinX;
                            envoie.ajouterMessage(demandeChangementCaseInventaire);
                            Reseau.writer.writeObject(envoie);
                            Reseau.writer.flush();
                        }
                    }
                }
            }
        }
    }



    private void actionCase(int index) {
        switch (index) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            case 16:
                break;
            case 17:
                break;
            case 18:
                break;
            case 19:
                break;
            case 20:
                break;
            case 21:
                break;
            case 22:
                break;
            case 23:
                break;
            case 24:
                break;
            case 25:
                break;
            case 26:
                break;
            case 27:
                break;
            case 28:
                break;
            case 29:
                break;
            case 30:
                break;
            case 31:
                break;
            case 32:
                break;
            default:
                BUG();
                break;
        }
    }


    private void BUG() {
        System.out.println("Cette case n'existe pas");
    }

    public void dispose() {
        fondInventaire.dispose();
        caseTexture.dispose();
        caseTexture.dispose();
//        bouleDeFeuTexture.dispose();
//        zoneDeSoinTexture.dispose();
//        zoneDeDegatsTexture.dispose();
//        rayonLaserTexture.dispose();
//        potionTexture.dispose();
//        potionVitesseTexture.dispose();
        font.dispose();
    }

    public boolean isVisible() {
        return visible;
    }
}
