package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.DinoDemo;
import com.mygdx.game.sprites.Cactus;
import com.mygdx.game.sprites.Dino;


public class PlayState extends State {
    private final Dino dino;
    private final Texture bg;
    private final BitmapFont font;
    private final MenuState ms;

    private final Array<Cactus> cactusArray;
    private long milSec;
    private final Sound jumpSound;
    private final Sound levelUpSound;
    private final Sound dieSound;
    private int score = 0;
    private int speed = 300;
    private final int record;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        dino = new Dino(20, 30);
        camera.setToOrtho(false, DinoDemo.WIDTH / 2f, DinoDemo.HEIGHT / 2f);
        bg = new Texture("background.jpg");
        ms = new MenuState(gsm);

        font = new BitmapFont();
        record = ms.getScore();

        milSec = TimeUtils.millis();

        // Sounds initialization
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        levelUpSound = Gdx.audio.newSound(Gdx.files.internal("point.wav"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("die.wav"));

        cactusArray = new Array<>();

        for (int i = 0; i < 2; i++) {
            cactusArray.add(new Cactus(30));
            System.out.println(cactusArray.size);
            System.out.println(cactusArray.get(i).getBounds().x);
        }

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (dino.getBounds().y == 30) {
                dino.jump();
                jumpSound.play();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        dino.update(dt);
        camera.position.x = dino.getPosition().x + 200;

        for (int i = 0; i < cactusArray.size; i++) {
            Cactus cactus = cactusArray.get(i);
            Cactus lastCactus = cactusArray.get(cactusArray.size - 1);

            cactus.getBounds().x -= speed * Gdx.graphics.getDeltaTime();

            if (lastCactus.getBounds().x < 300)
                cactusArray.add(new Cactus(30));

            if (cactus.collides(dino.getBounds())) {
                dieSound.play();
                ms.setScore(score);
                gsm.set(new MenuState(gsm));
            }
        }


        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        font.draw(sb, "HI:" + record, 380, 350);
        font.draw(sb, "Score:" + score, 450, 350);

        sb.draw(dino.getDino(), dino.getPosition().x, dino.getPosition().y, 80, 100);

        for (int i = 0; i < cactusArray.size; i++) {

            Cactus cactus = cactusArray.get(i);
            sb.draw(cactus.getCactusImage(), cactus.getBounds().x, cactus.getBounds().y);
        }
        sb.end();

        milSec++;
        if (milSec % 10 == 0) {
            score++;
        }

        if (score % 100 == 0 && score != 0) {
            levelUpSound.play();
            speed += 5;
        }

    }

    @Override
    public void dispose() {
        bg.dispose();
        dino.dispose();

        for (Cactus cactus : cactusArray) {
            cactus.dispose();
        }
    }

}
