package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cactus {
    private final Texture cactusImage;
    private final Rectangle bounds;

    public Cactus(float y) {
        cactusImage = new Texture("cactus.png");
        Vector2 cactus = new Vector2(1300, y);
        bounds = new Rectangle(cactus.x, cactus.y, cactusImage.getWidth(), cactusImage.getHeight());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getCactusImage() {
        return cactusImage;
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void dispose() {
        cactusImage.dispose();
    }
}
