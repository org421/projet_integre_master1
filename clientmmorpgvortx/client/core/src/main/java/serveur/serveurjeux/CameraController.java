package serveur.serveurjeux;

import com.badlogic.gdx.graphics.OrthographicCamera;
import serveur.serveurjeux.DTO.Entity.Personnage;
import com.badlogic.gdx.Gdx;

public class CameraController {
    private OrthographicCamera camera;

    public CameraController(float viewportWidth, float viewportHeight) {
        this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
        this.camera.position.set(viewportWidth / 2, viewportHeight / 2, 0);
        this.camera.update();
    }

    public void followEntity(Personnage personnage) {
        if (personnage != null) {
            float targetX = personnage.getEntite().getX() * 32;
            float targetY = personnage.getEntite().getY() * 32;

//             Correction : inverser Y pour s'adapter à l'axe Y de LibGDX
            float correctedY = Gdx.graphics.getHeight() - targetY;

            camera.position.set(targetX, correctedY, 0);
            camera.update();
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
