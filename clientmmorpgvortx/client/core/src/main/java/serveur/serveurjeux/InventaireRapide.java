package serveur.serveurjeux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import serveur.serveurjeux.DTO.Client.caseInventaireDTOClient;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.typeMessage.DemandeCaseSelection;
import serveur.serveurjeux.DTO.typeMessage.DemandeChangementCaseInventaire;
import serveur.serveurjeux.DTO.typeMessage.DemandeCreationPersonnage;
import serveur.serveurjeux.Entity.PersonnageJoueur;import serveur.serveurjeux.DTO.Client.caseInventaireDTOClient;
import serveur.serveurjeux.Entity.PersonnageJoueur;
import serveur.serveurjeux.informationClient.Reseau;
import serveur.serveurjeux.informationClient.Reseau;

import java.io.IOException;


public class InventaireRapide {
    private final int casesParLigne = 8;
    private final int tailleCase = 50;
    private final int espaceEntreCases = 5;

    private final int largeurTotal = (casesParLigne * (tailleCase + espaceEntreCases)) - espaceEntreCases;
    private final int x = (Gdx.graphics.getWidth() - largeurTotal) / 2; // Centrer en bas de l'écran
    private final int y = 10; // Marge depuis le bas

    private Texture caseTexture;

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


    public InventaireRapide() {
        caseTexture = new Texture("ig/fond_inv.png");  // Texture des cases
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
    }


    public void render(SpriteBatch batch) {
        for (int i = 0; i < casesParLigne; i++) {
            int caseX = x + i * (tailleCase + espaceEntreCases);
            batch.draw(caseTexture, caseX, y, tailleCase, tailleCase);


        if (PersonnageJoueur.inventaire != null && PersonnageJoueur.inventaire.inventaireDTOClients[4][i] != null) {
            caseInventaireDTOClient caseInv = PersonnageJoueur.inventaire.inventaireDTOClients[4][i];
            if (caseInv.type == 0) {
                switch (caseInv.typeObjet) {
                    case 1:
                        batch.draw(epeeTexture, caseX + 2, y + 2, 45, 45);

                        break;
                    case 2:
                        batch.draw(casqueTexture, caseX + 2, y + 2, 45, 45);

                        break;
                    case 3:
                        batch.draw(plastronTexture, caseX + 2, y + 2, 45, 45);

                        break;
                    case 4:
                        batch.draw(bottesTexture, caseX + 2, y + 2, 45, 45);

                        break;
                    case 5:
                        batch.draw(gantsTexture, caseX + 2, y + 2, 45, 45);

                        break;
                    case 6:
                        switch (caseInv.id) {
                            case 1:
                                System.out.print("cas 1 de la potion");
                                batch.draw(potionTexture, caseX + 2,y + 2, 45, 45);
                                break;
                            case 2:
                                System.out.print("cas 2 de la potion");
                                batch.draw(potionVitesseTexture, caseX + 2, y + 2, 45, 45);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 7:
                        batch.draw(caillouxTexture, caseX + 2, y + 2, 45, 45);

                        break;
                    default:
                        break;
                }
            }
            if (caseInv.type == 1) {
                switch (caseInv.id) {
                    case 1:
                        batch.draw(petiteAttaqueTexture, caseX + 2, y + 2, 45, 45);
                        break;
                    case 2:
                        batch.draw(rayonLaserTexture, caseX + 2, y + 2, 45, 45);
                        break;
                    case 3:
                        batch.draw(bouleDeFeuTexture, caseX + 2, y + 2, 45, 45);
                        break;
                    case 4:
                        batch.draw(zoneDeDegatsTexture, caseX + 2, y + 2, 45, 45);
                        break;
                    case 5:
                        batch.draw(zoneDeSoinTexture, caseX + 2, y + 2, 45, 45);
                        break;
                    default:
                        break;
                }

            }
        }

        }









    }

    public void handleClick(int screenX, int screenY) throws IOException {
        int worldY = Gdx.graphics.getHeight() - screenY; // Conversion en coordonnées du monde

        for (int i = 0; i < casesParLigne; i++) {
            int caseX = x + i * (tailleCase + espaceEntreCases);
            Rectangle caseRect = new Rectangle(caseX, y, tailleCase, tailleCase);

            if (caseRect.contains(screenX, worldY)) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                    // Si clic droit, on stocke l'index X et Y
                    FirstScreen.CaseInventaireSelectionnerX = 4; // Ligne fixe pour l'inventaire rapide
                    FirstScreen.CaseInventaireSelectionnerY = i;
                    System.out.println("Sélection via clic droit (Inventaire Rapide): " + i);
                    Inventaire.CaseInventaireSelectionnerX = 4;
                    Inventaire.CaseInventaireSelectionnerY = i;


                } else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    // Si clic gauche, on fait l'échange d'objets
                    if (FirstScreen.isFirstSelection) {
                        FirstScreen.CaseInventaireDepartX = 4;
                        FirstScreen.CaseInventaireDepartY = i;
                        FirstScreen.isFirstSelection = false;
                        System.out.println("Première sélection (Inventaire Rapide): " + i);
                    } else {
                        FirstScreen.CaseInventaireFinX = 4;
                        FirstScreen.CaseInventaireFinY = i;
                        FirstScreen.isFirstSelection = true;
                        System.out.println("Fin case " + FirstScreen.CaseInventaireFinX + "  " + FirstScreen.CaseInventaireFinY);

                        // Envoi des données au serveur
                        Envoie envoie = new Envoie(Reseau.ip, Reseau.pseudo, Reseau.UUID);
                        DemandeChangementCaseInventaire demandeChangementCaseInventaire = new DemandeChangementCaseInventaire();
                        demandeChangementCaseInventaire.indexXDepart = FirstScreen.CaseInventaireDepartY;
                        demandeChangementCaseInventaire.indexYDepart = FirstScreen.CaseInventaireDepartX;
                        demandeChangementCaseInventaire.indexXArrivee = FirstScreen.CaseInventaireFinY;
                        demandeChangementCaseInventaire.indexYArrivee = FirstScreen.CaseInventaireFinX;
                        envoie.ajouterMessage(demandeChangementCaseInventaire);
                        Reseau.writer.writeObject(envoie);
                        Reseau.writer.flush();
                        Reseau.writer.reset();
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
            default:
                BUG();
                break;
        }
    }

    private void BUG() {
        System.out.println("Cette case n'existe pas");
    }



    public void dispose() {

        caseTexture.dispose();
//        bouleDeFeuTexture.dispose();
//        zoneDeSoinTexture.dispose();
//        zoneDeDegatsTexture.dispose();
//        rayonLaserTexture.dispose();
//        potionTexture.dispose();
//        potionVitesseTexture.dispose();

    }
}
