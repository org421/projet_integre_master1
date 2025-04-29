package serveur.serveurjeux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import serveur.serveurjeux.DTO.Entity.Entite;
import serveur.serveurjeux.DTO.Entity.Monstre;
import serveur.serveurjeux.DTO.Entity.Personnage;
import serveur.serveurjeux.DTO.Entity.Sort;
import serveur.serveurjeux.Entity.PersonnageJoueur;
import serveur.serveurjeux.informationClient.Reseau;
import serveur.serveurjeux.utility.MapManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstScreen implements Screen {
    public final int MAXTEXTURES = 100; //nombre d'imagse de carte max
    public final int TAILLEZONE = 1024; //Taille de chaque zone de la carte (en pixel) (taille normale : 1024)
    public final String NOMCARTE = "main"; //Le dossier contenant toutes les informations de la carte (images et JSON)
                                                //Les images, le dossier et le JSON doivent commencer par le nom de la carte
    private SpriteBatch batch;
    private Animation<TextureRegion> eauAnimation;
    private Texture joueurTexture;
    private Texture scorpionTexture;
    private CameraController cameraController;
    private Texture[] textures;
    private float stateTime = 0;
    private Texture[] carteTextures = new Texture[MAXTEXTURES]; // Toutes les images qui composent la carte
    private Map<String, Object> carteDonnees; // Utiliser pour stocker les données relatives au placement des images de la carte

    //les dimensions de la carte
    private int largeurCarte = 0;
    private int hauteurCarte = 0;

    public static List<Personnage> personnageProche = new ArrayList<>();
    public static List<Monstre> monstreProche = new ArrayList<>();


    private Texture contour_texture;
    private Texture contour_hp_texture ;
    private Texture menu_ui_texture;
    private Texture menu_ui_texture_2;
    private Texture coeur_ui ;
    private Texture epee_ui ;
    private Texture armure_ui ;
    private Texture contour_xp_texture;
    private Texture argentTexture ;
    private int debug_menu = 0;


    private BitmapFont font; // Ajout de la déclaration
    private final int screenWidth = Gdx.graphics.getWidth();

    //ui inventaire
    private Inventaire inventaire;
    private InventaireRapide inventaireRapide;

    public static boolean isFirstSelection = true;
    public static int CaseInventaireDepartX;
    public static int CaseInventaireDepartY;
    public static int CaseInventaireFinX;
    public static int CaseInventaireFinY;

    public static int CaseInventaireSelectionnerX;
    public static int CaseInventaireSelectionnerY;


    public static List<Sort> sortAffichage = new ArrayList<>();


    private Animation<TextureRegion> SortPetitCoupAnimation;




    private Texture joueurTextureGuerrier;
    private Texture joueurTextureArcher;
    private Texture joueurTextureGrappler;
    private Texture joueurTextureMage;




    @Override
    public void show() {
        batch = new SpriteBatch();
//        eauAnimation = FonctionAnimation.loadAnimation("eau", "eau_frame_", 10, 0.9f);
        eauAnimation = FonctionAnimation.loadAnimation("newEau", "sprite_eau", 7, 0.9f);

        joueurTextureGuerrier= new Texture("SpriteClasse/Guerrier/ide.png");
        joueurTextureArcher =  new Texture("SpriteClasse/Archer/ide2.png");
        joueurTextureGrappler  =  new Texture("SpriteClasse/Grappler/ide2.png");
        joueurTextureMage  =  new Texture("SpriteClasse/Mage/ide2.png");

        SortPetitCoupAnimation = FonctionAnimation.loadAnimation("sort1", "", 14, 0.9f);

        joueurTexture = new Texture("SpriteClasse/Guerrier/ide.png");
        scorpionTexture = new Texture("monstres/Scorpion.png");

        // Création de la caméra
        cameraController = new CameraController(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //ui_in_game
        contour_texture = new Texture(Gdx.files.internal("ig/contour.png"));
        contour_hp_texture = new Texture(Gdx.files.internal("ig/contour_hp.png"));
        menu_ui_texture = new Texture(Gdx.files.internal("ig/menu_ui.png"));
        menu_ui_texture_2 = new Texture(Gdx.files.internal("ig/SimplePanel.png"));
        coeur_ui = new Texture(Gdx.files.internal("ig/coeur.png"));
        epee_ui = new Texture(Gdx.files.internal("ig/epee_iu.png"));
        armure_ui = new Texture(Gdx.files.internal("ig/armure_iu.png"));
        contour_xp_texture = new Texture(Gdx.files.internal("ig/contour.png"));
        argentTexture = new Texture(Gdx.files.internal("ig/argent_ui.png"));
        font = new BitmapFont(); // Initialisation de la police

        // Générer une police Bitmap à partir d'un fichier .ttf
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ig/Daydream.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 16;  // Taille de la police
        font = generator.generateFont(parameter); // Crée une police BitmapFont à partir du .ttf
        generator.dispose(); // Libère le générateur après usage



        //INVENTAIREEEEEEEEEEEEEEEEEEE

        inventaire = new Inventaire();
        inventaireRapide = new InventaireRapide();





        //chargement des images des textures de la carte
        // On charge les données du fichier Json contenant les informations des coordonnées des images et des dimensions de la carte
        try {
            carteDonnees = MapManager.chargerJSON("assets/map/"+NOMCARTE+"/"+NOMCARTE+".json");
            Map<String, Map<String, Integer>> images = MapManager.imagesJson(carteDonnees);

            largeurCarte = MapManager.largeurJSON(carteDonnees);
            hauteurCarte = MapManager.hauteurJSON(carteDonnees);
            System.out.println("Carte: -> Dimension: " + hauteurCarte + "," + largeurCarte);

            // On cherche dans le répertoire où sont stockées les textures de la carte,
            // ATTENTION : toutes les images dans ce répertoire sont considérée comme étant UNE SEULE et même carte, si on veut une autre carte, la stockée dans un autre répertoire et cherché dedans
            File repertoire = new File("assets/map/"+NOMCARTE);
            int index;
            if (repertoire.exists() && repertoire.isDirectory()) { // on check si le répertoire existe, et s'il s'agit bien d'un répertoire
                index = 0;
                // on regarde chaque donnée d'image du Json
                String imageNom;
                Map<String, Integer> coords;
                for (Map.Entry<String, Map<String, Integer>> image : images.entrySet()) {

                    imageNom = image.getKey();
                    coords = image.getValue();

                    //System.out.println("Image: " + image.getKey() + " -> Coordonnées: " + coords.get("x") + "," + coords.get("y"));
                    carteTextures[index] = new Texture("map/"+NOMCARTE+"/"+ imageNom);
                    index ++;
                }
            } else {
               // System.out.println("Le chemin spécifié n'est pas un répertoire valide.");
            }
        } catch (Exception e) {
            e.printStackTrace();
           // System.out.println("Le fichier Json est manquant, ou ne peut pas être lu");
        }
    }


    //--------------------------------------------------------//
    //
    //        J'explique pour les gens qui vont relire
    //        render c'est une fonction qui se lance en boucle
    //        donc les fonctions de reception de touche
    //        sont lus en permanence ce qui permettra
    //        d'envoyer au serveur les touche h24
    //
    //--------------------------------------------------------//
    @Override
    public void render(float delta) {
        try {
            ReceptionToucheEtSouris.receptionToucheAppuyer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ReceptionToucheEtSouris.receptionClicSourisAvecDirection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Mettre à jour la position de la caméra
        if (PersonnageJoueur.personnage != null) {
            cameraController.followEntity(PersonnageJoueur.personnage);
        }

        batch.setProjectionMatrix(cameraController.getCamera().combined);

        batch.begin();

        int screenHeight = Gdx.graphics.getHeight();  // Récupère la hauteur de l'écran

        // Correction de l'affichage du joueur
        float joueurX = PersonnageJoueur.personnage.getEntite().getX() * 32;
        float joueurY = Gdx.graphics.getHeight() - PersonnageJoueur.personnage.getEntite().getY() * 32;

       // System.out.println("joueur : " + joueurX + " , " + joueurY);

        // on va dessiner les images de la carte en fonction de la position du personnage
        // ATTENTION : il faut que les donnees dans le carteTextures et dans carteDonnees soit dans le même sens

        int index = 0;
        int zoneJoueurX = (int) Math.floor((joueurX/32)/32); //Coord X de la zone où se trouve le joueur
        int zoneJoueurY = (int) Math.floor((joueurY/32)/32); //Coord Y de la zone où se trouve le joueur

       // System.out.println("zone du joueur :" + zoneJoueurY + " " + zoneJoueurX);

        Map<String, Integer> coords;
        TextureRegion eau = eauAnimation.getKeyFrame(stateTime, true); // Récupère le frame actuel

        // On dessine de l'eau dans les chunks autour du personnage en tant qu'arrière plan
        batch.draw(eau, (zoneJoueurX-1) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY-1)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX-1) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX-1) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY+1)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY-1)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY+1)-1) * TAILLEZONE+32, TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX+1) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY-1)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX+1) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);
        batch.draw(eau, (zoneJoueurX+1) * TAILLEZONE, Gdx.graphics.getHeight() + ((zoneJoueurY+1)-1) * TAILLEZONE+32 , TAILLEZONE, TAILLEZONE);

        for (Map.Entry<String, Map<String, Integer>> image : MapManager.imagesJson(carteDonnees).entrySet()) {
            coords = image.getValue();
//            System.out.println("value: " + image.getValue());
//            System.out.println("key: " + image.getKey());
            if(coords.get("x") == zoneJoueurX - 1 && -coords.get("y") == zoneJoueurY-1 ||
                coords.get("x") == zoneJoueurX - 1 && -coords.get("y") == zoneJoueurY ||
                coords.get("x") == zoneJoueurX - 1 && -coords.get("y") == zoneJoueurY+1 ||
                coords.get("x") == zoneJoueurX && -coords.get("y") == zoneJoueurY+1 ||
                coords.get("x") == zoneJoueurX + 1 && -coords.get("y") == zoneJoueurY+1 ||
                coords.get("x") == zoneJoueurX + 1 && -coords.get("y") == zoneJoueurY ||
                coords.get("x") == zoneJoueurX + 1 && -coords.get("y") == zoneJoueurY-1 ||
                coords.get("x") == zoneJoueurX && -coords.get("y") == zoneJoueurY-1 ||
                coords.get("x") == zoneJoueurX && -coords.get("y") == zoneJoueurY
            ) { //on ne dessine la zone que s'il s'agit de l'une des zones voisines à la zone où se trouve le joueur ou si le joueur se trouve dessus
                stateTime += Gdx.graphics.getDeltaTime(); // Met à jour le temps écoulé
                batch.draw(carteTextures[index], coords.get("x") * TAILLEZONE, Gdx.graphics.getHeight() + (-(coords.get("y")+1) * (TAILLEZONE)+32), TAILLEZONE, TAILLEZONE);

            }
            index ++;
        }

        // On dessine le joueur APRES la carte, sinon la carte est par-dessus le joueur
        if(PersonnageJoueur.personnage.getClasse() == 0) {
            batch.draw(joueurTextureGuerrier, joueurX - joueurTextureGuerrier.getWidth()/2, joueurY + joueurTextureGuerrier.getHeight(), joueurTextureGuerrier.getWidth(), joueurTextureGuerrier.getHeight());
        }
        if(PersonnageJoueur.personnage.getClasse() == 1) {
            batch.draw(joueurTextureArcher, joueurX - joueurTextureArcher.getWidth()/2, joueurY + joueurTextureArcher.getHeight(), joueurTextureArcher.getWidth(), joueurTextureArcher.getHeight());
        }
        if(PersonnageJoueur.personnage.getClasse() == 2) {
            batch.draw(joueurTextureMage, joueurX - joueurTextureMage.getWidth()/2, joueurY + joueurTextureMage.getHeight(), joueurTextureMage.getWidth(), joueurTextureMage.getHeight());
        }
        if(PersonnageJoueur.personnage.getClasse() == 3) {
            batch.draw(joueurTextureGrappler, joueurX - joueurTextureGrappler.getWidth()/2, joueurY + joueurTextureGrappler.getHeight(), joueurTextureGrappler.getWidth(), joueurTextureGrappler.getHeight());
        }

        // Correction de l'affichage des autres personnages

        List<Personnage> personnages = new ArrayList<>(personnageProche);
        for (Personnage personnage : personnages) {
            float personnageX = personnage.getEntite().getX() * 32;
            float personnageY = Gdx.graphics.getHeight() - personnage.getEntite().getY() * 32;

            if(personnage.getClasse() == 0) {
                batch.draw(joueurTextureGuerrier, personnageX - joueurTextureGuerrier.getWidth()/2, personnageY + joueurTextureGuerrier.getHeight(), joueurTextureGuerrier.getWidth(), joueurTextureGuerrier.getHeight());
            }
            if(personnage.getClasse() == 1) {
                batch.draw(joueurTextureArcher, personnageX - joueurTextureArcher.getWidth()/2, personnageY + joueurTextureArcher.getHeight(), joueurTextureArcher.getWidth(), joueurTextureArcher.getHeight());
            }
            if(personnage.getClasse() == 2) {
                batch.draw(joueurTextureMage, personnageX - joueurTextureMage.getWidth()/2, personnageY + joueurTextureMage.getHeight(), joueurTextureMage.getWidth(), joueurTextureMage.getHeight());
            }
            if(personnage.getClasse() == 3) {
                batch.draw(joueurTextureGrappler, personnageX - joueurTextureGrappler.getWidth()/2, personnageY + joueurTextureGrappler.getHeight(), joueurTextureGrappler.getWidth(), joueurTextureGrappler.getHeight());
            }
        }

        //Affichage des monstres
        List<Monstre> monstres = new ArrayList<>(monstreProche);
        for (Monstre monstre : monstres) {
            float monstreX = monstre.getX() * 32;
            float monstreY = Gdx.graphics.getHeight() - monstre.getY() * 32;
            // modif avec la bonne texture en fonction de typeMonstre
            batch.draw(scorpionTexture, monstreX - scorpionTexture.getWidth()/2, monstreY + scorpionTexture.getHeight(), scorpionTexture.getWidth(), scorpionTexture.getHeight());
        }

        //affichage sort
        TextureRegion sort1 = SortPetitCoupAnimation.getKeyFrame(stateTime, true); // Récupère le frame actuel


        List<Sort> sorts = new ArrayList<>(sortAffichage);
        for(Sort sort : sorts){
            float sortX = sort.getX() * 32;
            float sortY = Gdx.graphics.getHeight() - sort.getY() * 32;
            int id = sort.getId_competence();

            switch (id) {
                case 1:
                    batch.draw(sort1, sortX, sortY);
                break;
                case 2:
                    batch.draw(sort1, sortX, sortY);
                    break;
                default:
                    System.out.println("Sort sans affichage ");
                    break;
            }
        }






        // MENU
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch.draw(menu_ui_texture, 10, 10, 300, 150);  // Position en bas à gauche, avec un léger décalage
        batch.draw(contour_hp_texture, 45, 30, PersonnageJoueur.personnage.getEntite().getPv() * 2, 32);  // Barre de HP à une position fixe
        batch.draw(contour_texture, 45, 30, PersonnageJoueur.personnage.getEntite().getPvMax() * 2, 32);  // Barre de HP max à la même position
        batch.draw(coeur_ui, (PersonnageJoueur.personnage.getEntite().getPvMax() * 2) + 45, 30);
        batch.draw(epee_ui, 45, 70);
        batch.draw(armure_ui, 45, 110);
        font.draw(batch, Integer.toString(PersonnageJoueur.personnage.getEntite().getPointAttaque()), 100, 100);
        font.draw(batch, Integer.toString(PersonnageJoueur.personnage.getEntite().getPointArmure()), 100, 140);



        batch.draw(menu_ui_texture, screenWidth - 310, 10, 300, 150);  // Position en bas à gauche, avec un léger décalage
        batch.draw(contour_xp_texture, screenWidth - (75 + (100 * 2)), 30, PersonnageJoueur.personnage.getEntite().getXp() * 2, 32);  // Barre de HP à une position fixe
        batch.draw(contour_texture, screenWidth - (75 + (100 * 2)), 30, PersonnageJoueur.personnage.getEntite().getPvMax() * 2, 32);  // Barre de HP max à la même position
        batch.draw(menu_ui_texture, screenWidth - 274, 62, 60, 30);
        batch.draw(argentTexture, screenWidth - 274, 95, 40, 40);
        font.draw(batch, Integer.toString(PersonnageJoueur.personnage.getEntite().getLevel()), screenWidth - 260, 92);
        font.draw(batch, Integer.toString(PersonnageJoueur.personnage.getArgent()), screenWidth - 230, 128);


        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            debug_menu = debug_menu + 1;
            debug_menu = debug_menu % 2;
        }

        if (debug_menu == 0) {
            batch.draw(menu_ui_texture, 10,620 , 320, 100);  // Position en bas à gauche, avec un léger décalage
            font.draw(batch,"X :" + Float.toString(PersonnageJoueur.personnage.getEntite().getX()), 25, 710);
            font.draw(batch,"Y :" +Float.toString(PersonnageJoueur.personnage.getEntite().getY()), 25, 680);
            font.draw(batch,"Joueurs Visible :" + personnageProche.size(), 25, 650);
            font.draw(batch,"Monstre Visible :" + monstreProche.size(), 25, 600);
        }

        /************************************************
                 INVENTAIRE AVEC I pour l'ouvrir
         ************************************************/


        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            inventaire.toggle();
        }
        if (inventaire.isVisible()) {
            inventaire.render(batch);
//            System.out.println("INVENTAIRE");
//            for (int i = 0; i < PersonnageJoueur.inventaire.inventaireDTOClients.length; i++) {
//                for (int j = 0; j < PersonnageJoueur.inventaire.inventaireDTOClients[i].length; j++) {
//                    System.out.print(PersonnageJoueur.inventaire.inventaireDTOClients[i][j] != null
//                        ? PersonnageJoueur.inventaire.inventaireDTOClients[i][j].nom
//                        : "Vide");
//                    System.out.print(" | ");
//                }
//                System.out.println();
//            }

        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            if (inventaire.isVisible()) {
                try {
                    inventaire.handleClick(Gdx.input.getX(), Gdx.input.getY());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    ReceptionToucheEtSouris.receptionClicSourisAvecDirection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /************************************************
                        Inventaire d en bas
         ************************************************/
        inventaireRapide.render(batch);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            try {
                inventaireRapide.handleClick(Gdx.input.getX(), Gdx.input.getY());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        batch.setProjectionMatrix(cameraController.getCamera().combined);

        //debugage

        if(Gdx.input.isKeyPressed(Input.Keys.L)){
            System.out.println(PersonnageJoueur.personnage.getEntite().getPv());
            System.out.println(PersonnageJoueur.personnage.getEntite().getPvMax());
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        cameraController.getCamera().setToOrtho(false, width, height);
        cameraController.getCamera().position.set(width / 2, height / 2, 0);
        cameraController.getCamera().update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        for (Texture texture : textures) {
            texture.dispose();
        }

        joueurTexture.dispose();
        joueurTextureGuerrier.dispose();
        joueurTextureArcher.dispose();
        joueurTextureMage.dispose();
        joueurTextureGrappler.dispose();

        contour_texture.dispose();
        contour_hp_texture.dispose();
        menu_ui_texture.dispose();
        menu_ui_texture_2.dispose();
        coeur_ui.dispose();
        epee_ui.dispose();
        armure_ui.dispose();
        contour_xp_texture.dispose();
        argentTexture.dispose();
        font.dispose(); // Libération de la police
        inventaire.dispose();
        inventaireRapide.dispose();



    }
}
