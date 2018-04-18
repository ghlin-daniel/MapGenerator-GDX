package com.brickgit.mapgeneratorgdx.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brickgit.mapgeneratorgdx.actors.BaseDrawer;
import com.brickgit.mapgeneratorgdx.utils.Assets;
import com.brickgit.mapgeneratorgdx.utils.Config;

/**
 * Created by Daniel Lin on 15/04/2018.
 */

public class DrawerScreen extends BaseScreen {

    private BaseDrawer drawer;

    public DrawerScreen(BaseDrawer drawer) {
        super();

        this.drawer = drawer;
        stage.addActor(drawer);

        addBackButton();
        addStartButton();
    }

    private void addBackButton() {
        TextButton btnBack = new TextButton("Back", Assets.getSkin(), "colored");
        btnBack.setSize(80, 50);
        btnBack.setPosition(
                10,
                5);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openScreen(new MenuScreen());
            }
        });
        stage.addActor(btnBack);
    }

    private void addStartButton() {
        final TextButton btnStart = new TextButton("Start", Assets.getSkin(), "colored");
        btnStart.setSize(80, 50);
        btnStart.setPosition(
                (Config.WINDOW_WIDTH - btnStart.getWidth()) / 2,
                5);
        btnStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnStart.setTouchable(Touchable.disabled);
                drawer.start();
            }
        });
        stage.addActor(btnStart);
    }
}
