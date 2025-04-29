package serveur.serveurjeux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FonctionAnimation {

    public static Animation<TextureRegion> loadAnimation(String folderPath, String framePrefix, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];

        for (int i = 0; i < frameCount; i++) {
            Texture texture = new Texture(Gdx.files.internal(folderPath + "/" + framePrefix + (i + 1) + ".png"));
            frames[i] = new TextureRegion(texture);
        }

        return new Animation<>(frameDuration, frames);
    }
}
