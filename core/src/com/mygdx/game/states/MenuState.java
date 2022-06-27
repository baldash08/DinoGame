package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.DinoDemo;

public class MenuState extends State {

    private final Texture background;
    private final Texture playBtn;
    private static int score = 0;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, DinoDemo.WIDTH / 2f, DinoDemo.HEIGHT / 2f);
        background = new Texture("background.jpg");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn, camera.position.x - playBtn.getWidth() / 2f, camera.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }

    public void setScore(int score) {
        if (MenuState.score < score)
            MenuState.score = score;
    }

    public int getScore() {
        return score;
    }

}