package com.brickgit.mapgeneratorgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.brickgit.mapgeneratorgdx.MainGame;
import com.brickgit.mapgeneratorgdx.utils.Config;

/**
 * Created by Daniel Lin on 13/04/2018.
 */

public abstract class BaseScreen implements Screen {

    protected Stage stage;

    public BaseScreen() {
        stage = new Stage();
        stage.setViewport(
                new FitViewport(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    protected void openScreen(Screen screen) {
        ((MainGame) Gdx.app.getApplicationListener()).setScreen(screen);
        dispose();
    }
}
