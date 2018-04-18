package com.brickgit.mapgeneratorgdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Daniel Lin on 10/04/2018.
 */

public class Assets {

    private static Skin skin;
    private static Texture wall;
    private static Texture stairUp;
    private static Texture stairDown;
    private static Texture heroFemale;
    private static Texture heroMale;
    private static BitmapFont bitmapFont;

    public static Skin getSkin() {
        return skin;
    }

    public static Texture getWall() {
        return wall;
    }

    public static Texture getStairUp() {
        return stairUp;
    }

    public static Texture getStairDown() {
        return stairDown;
    }

    public static Texture getHeroFemale() {
        return heroFemale;
    }

    public static Texture getHeroMale() {
        return heroMale;
    }

    public static BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public static void init() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        wall = new Texture("wall.png");
        stairUp = new Texture("stair-up.png");
        stairDown = new Texture("stair-down.png");
        heroFemale = new Texture("hero-f.png");
        heroMale = new Texture("hero-m.png");
        bitmapFont = new BitmapFont(
                Gdx.files.internal("default.fnt"),
                Gdx.files.internal("default.png"), false);
    }

    public static void dispose() {
        skin.dispose();
        wall.dispose();
        stairUp.dispose();
        stairDown.dispose();
        heroFemale.dispose();
        heroMale.dispose();
        bitmapFont.dispose();
    }
}
