package serveur.serveurjeux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Input.Buttons;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.typeMessage.DemandeCreationPersonnage;
import serveur.serveurjeux.informationClient.Reseau;

import java.io.IOException;

public class ClassePage implements Screen {
    private Stage stage;
    private Skin skin;
    private Label characterLabel;
    private Image characterImage;
    private TextButton leftButton, rightButton, confirmButton;
    private SpriteBatch batch;

    // Animation du fond
    private Animation<TextureRegion> backgroundAnimation;
    private float stateTime = 0;

    private String[] characterNames = {"Guerrier", "Archer", "Mage", "Grappler"};
    private String[] characterImages = {"logoClasse/guerrier.png", "logoClasse/archer.png", "logoClasse/mage.png", "logoClasse/grappler.png"};
    private int currentIndex = 0;
    private Texture currentTexture;

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        backgroundAnimation = FonctionAnimation.loadAnimation("fond_creation", "fonds_frame_", 39, 0.1f);

        // Interface utilisateur
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        characterLabel = new Label(characterNames[currentIndex], skin);
        characterLabel.setFontScale(2);

        currentTexture = new Texture(Gdx.files.internal(characterImages[currentIndex]));
        characterImage = new Image(currentTexture);

        leftButton = new TextButton("<", skin);
        rightButton = new TextButton(">", skin);
        confirmButton = new TextButton("Confirmer", skin);

        leftButton.getLabel().setFontScale(2);
        rightButton.getLabel().setFontScale(2);
        confirmButton.getLabel().setFontScale(2);

        // Vérifier que l'utilisateur clique avec le bouton gauche
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getButton() == Buttons.LEFT) { // Vérifie si c'est un clic gauche
                    currentIndex = (currentIndex - 1 + characterNames.length) % characterNames.length;
                    updateCharacterSelection();
                }
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getButton() == Buttons.LEFT) { // Vérifie si c'est un clic gauche
                    currentIndex = (currentIndex + 1) % characterNames.length;
                    updateCharacterSelection();
                }
            }
        });

        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getButton() == Buttons.LEFT) { // Vérifie si c'est un clic gauche
                    System.out.println("Personnage sélectionné : " + currentIndex);
                    Envoie envoie = new Envoie(Reseau.ip,Reseau.pseudo,Reseau.UUID);
                    DemandeCreationPersonnage demandeCreationPersonnage = new DemandeCreationPersonnage();
                    demandeCreationPersonnage.classPersonnage = currentIndex;
                    envoie.ajouterMessage(demandeCreationPersonnage);
                    try {
                        Reseau.writer.writeObject(envoie);
                        Reseau.writer.flush();
                        Main mainGame = (Main) Gdx.app.getApplicationListener();
                        mainGame.setScreen(new FirstScreen());

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        table.add(leftButton).pad(20).size(100, 25);
        table.add(characterLabel).pad(20);
        table.add(rightButton).pad(20).size(100, 25);
        table.row();
        table.add(characterImage).colspan(3).pad(20).size(128, 128);
        table.row();
        table.add(confirmButton).colspan(3).center().pad(20).width(200).height(60);
    }

    private void updateCharacterSelection() {
        characterLabel.setText(characterNames[currentIndex]);
        currentTexture.dispose();
        currentTexture = new Texture(Gdx.files.internal(characterImages[currentIndex]));
        characterImage.setDrawable(new Image(currentTexture).getDrawable());
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        TextureRegion currentFrame = backgroundAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 0, 0, 1280, 720);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        currentTexture.dispose();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
        if (currentTexture != null) currentTexture.dispose();
        batch.dispose();
    }
}
