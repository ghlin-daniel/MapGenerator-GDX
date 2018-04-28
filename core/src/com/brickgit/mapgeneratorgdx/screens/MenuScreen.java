package com.brickgit.mapgeneratorgdx.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brickgit.mapgeneratorgdx.actors.BfsDrawer;
import com.brickgit.mapgeneratorgdx.actors.DfsDrawer;
import com.brickgit.mapgeneratorgdx.actors.DijkstrasDrawer;
import com.brickgit.mapgeneratorgdx.actors.MapDrawer;
import com.brickgit.mapgeneratorgdx.actors.WalkerDrawer;
import com.brickgit.mapgeneratorgdx.utils.Assets;
import com.brickgit.mapgeneratorgdx.utils.Config;

/**
 * Created by Daniel Lin on 10/04/2018.
 */

public class MenuScreen extends BaseScreen {

    public MenuScreen() {
        super();
        initButtons();
    }

    private void initButtons() {
        Table table = new Table(Assets.getSkin());
        table.setSize(300, 400);
        table.setPosition((Config.WINDOW_WIDTH - table.getWidth()) / 2,
                (Config.WINDOW_HEIGHT - table.getHeight()) / 2);

        TextButton btnMapGenerator = new TextButton("Map Generator", Assets.getSkin(), "colored");
        btnMapGenerator.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openScreen(new DrawerScreen(new MapDrawer()));
            }
        });
        table.add(btnMapGenerator).width(200);
        table.row();

        TextButton btnMapWalker = new TextButton("Map Walker", Assets.getSkin(), "colored");
        btnMapWalker.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openScreen(new DrawerScreen(new WalkerDrawer()));
            }
        });
        table.add(btnMapWalker).width(200);
        table.row();

        TextButton btnDfs = new TextButton("Depth-First Search", Assets.getSkin(), "colored");
        btnDfs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openScreen(new DrawerScreen(new DfsDrawer()));
            }
        });
        table.add(btnDfs).width(200);
        table.row();

        TextButton btnBfs = new TextButton("Breadth-First Search", Assets.getSkin(), "colored");
        btnBfs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openScreen(new DrawerScreen(new BfsDrawer()));
            }
        });
        table.add(btnBfs).width(200);
        table.row();

        TextButton btnDijkstras = new TextButton("Dijkstra's Algorithm", Assets.getSkin(), "colored");
        btnDijkstras.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openScreen(new DrawerScreen(new DijkstrasDrawer()));
            }
        });
        table.add(btnDijkstras).width(200);
        table.row();

        stage.addActor(table);
    }
}
