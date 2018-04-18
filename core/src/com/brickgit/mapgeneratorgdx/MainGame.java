package com.brickgit.mapgeneratorgdx;

import com.badlogic.gdx.Game;
import com.brickgit.mapgeneratorgdx.screens.MenuScreen;
import com.brickgit.mapgeneratorgdx.utils.Assets;

public class MainGame extends Game {

	@Override
	public void create() {
		Assets.init();
		setScreen(new MenuScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		Assets.dispose();
	}
}
