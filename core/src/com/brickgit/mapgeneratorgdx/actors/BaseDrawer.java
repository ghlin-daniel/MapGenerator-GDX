package com.brickgit.mapgeneratorgdx.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.brickgit.mapgeneratorgdx.utils.Assets;
import com.brickgit.mapgeneratorgdx.utils.Config;

/**
 * Created by Daniel Lin on 15/04/2018.
 */

public abstract class BaseDrawer extends Actor {

    protected final static int sizeWall = Assets.getWall().getWidth();
    protected final static Texture wall = Assets.getWall();
    protected final static Texture stairUp = Assets.getStairUp();
    protected final static Texture stairDown = Assets.getStairDown();
    protected final static Texture heroFemale = Assets.getHeroFemale();
    protected final static Texture heroMale = Assets.getHeroMale();
    protected final static BitmapFont bitmapFont = Assets.getBitmapFont();

    private boolean isStarted = false;
    private long lastDrawingTime = 0;

    public BaseDrawer() {}

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        drawFps(batch);

        if (isStarted) {
            long now = TimeUtils.nanoTime();
            if (now - lastDrawingTime >= 50000000) {
                drawStepByStep(batch);
                lastDrawingTime = now;
            }
        }

        draw(batch);
    }

    public void start() {
        isStarted = true;
    }

    public abstract void draw(Batch batch);
    public abstract void drawStepByStep(Batch batch);

    private void drawFps(Batch batch) {
        String fps = "FPS: " + String.valueOf(Gdx.app.getGraphics().getFramesPerSecond());

        bitmapFont.getData().setScale(1f);
        bitmapFont.setColor(Color.WHITE);
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, fps);
        bitmapFont.draw(
                batch,
                glyphLayout,
                0,
                Config.WINDOW_HEIGHT);
    }
}
