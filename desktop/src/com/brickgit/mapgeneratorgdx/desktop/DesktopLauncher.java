package com.brickgit.mapgeneratorgdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brickgit.mapgeneratorgdx.utils.Config;
import com.brickgit.mapgeneratorgdx.MainGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Config.WINDOW_WIDTH;
		config.height = Config.WINDOW_HEIGHT;
		new LwjglApplication(new MainGame(), config);
	}
}
