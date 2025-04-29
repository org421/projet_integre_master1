package serveur.serveurjeux;

import com.badlogic.gdx.Game;
import serveur.serveurjeux.informationClient.ConnexionServeurJeux;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
       ConnexionServeurJeux connexionServeurJeux = new ConnexionServeurJeux();
       connexionServeurJeux.run();
//

        setScreen(new LoginPage());

        //setScreen(new FirstScreen());



    }
}
