package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Dino {
    public static final int GRAVITY = -10;
    private final Vector3 position;
    private final Vector3 velosity;
    private final Rectangle bounds;

    private final Texture dino;

    public Dino(int x, int y){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        dino = new Texture("dino.png");
        bounds = new Rectangle(x, y, dino.getWidth() - 70, dino.getHeight() - 60);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getDino() {
        return dino;
    }

    public void update(float dt) {
        if (position.y > 30)
            velosity.add(0, GRAVITY, 0);
        velosity.scl(dt);
        position.add(0, velosity.y, 0);

        if (position.y < 30)
            position.y = 30;

        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);


    }
    public void jump() {
        velosity.y = 400;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose() {
    }


}
