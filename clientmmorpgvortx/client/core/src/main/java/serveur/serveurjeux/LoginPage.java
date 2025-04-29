package serveur.serveurjeux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Screen;
import serveur.serveurjeux.Entity.PersonnageJoueur;
import serveur.serveurjeux.controller.ActionReseau;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class LoginPage implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Load background image
        backgroundTexture = new Texture(Gdx.files.internal("ui_jeu/Login_screen.png"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);



        Label usernameLabel = new Label("Pseudo :", skin);
        usernameLabel.getStyle().fontColor = com.badlogic.gdx.graphics.Color.BLACK;

        usernameLabel.setFontScale(4);
        TextField usernameField = new TextField("", skin);
        usernameField.setStyle(new TextField.TextFieldStyle(usernameField.getStyle()));
        usernameField.getStyle().font.getData().setScale(2);

        Label passwordLabel = new Label("Mot de passe :", skin);
        passwordLabel.getStyle().fontColor = com.badlogic.gdx.graphics.Color.BLACK;

        passwordLabel.setFontScale(4);
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setStyle(new TextField.TextFieldStyle(passwordField.getStyle()));
        passwordField.getStyle().font.getData().setScale(2);




        TextButton loginButton = new TextButton("Valider", skin);
        loginButton.getLabel().setFontScale(2);




        //**********************************************
        //
        //       Ce qu'il se passe quand on valide
        //
        //**********************************************
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
//                System.out.println("Tentative de connexion: " + username);

                try {
                    // Envoyer la demande de connexion au serveur
                    ActionReseau.EnvoieLogin(username, password);

                    // Définir un listener pour réagir à la réponse
                    ActionReseau.setLoginListener(success -> {
                        if (success) {
                            Gdx.app.postRunnable(() -> {
                                if (PersonnageJoueur.personnage != null) {
                                    Main mainGame = (Main) Gdx.app.getApplicationListener();
                                    mainGame.setScreen(new FirstScreen());
                                }else{
                                    Main mainGame = (Main) Gdx.app.getApplicationListener();
                                    mainGame.setScreen(new ClassePage());
                                }
                            });
                        } else {
                            System.out.println("Échec de la connexion");
                        }
                    });

                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de l'envoi des identifiants", e);
                }
            }
        });

        table.add(usernameLabel).pad(20);
        table.add(usernameField).width(400).height(60).pad(20);
        table.row();
        table.add(passwordLabel).pad(20);
        table.add(passwordField).width(400).height(60).pad(20);
        table.row();
        table.add(loginButton).colspan(2).center().pad(20).width(200).height(60);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        stage.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
    }
}
