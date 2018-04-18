package com.brickgit.mapgeneratorgdx.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.brickgit.mapgeneratorgdx.algorithms.DepthFirstSearch;
import com.brickgit.mapgeneratorgdx.algorithms.Map;
import com.brickgit.mapgeneratorgdx.utils.Config;
import com.brickgit.mapgeneratorgdx.utils.MapPoint;

import java.util.List;

/**
 * Created by Daniel Lin on 14/04/2018.
 */

public class DfsDrawer extends BaseDrawer {

    private Map map;
    private DepthFirstSearch dfs;
    private MapPoint handlingPosition;
    private Texture handlingArea;

    private int heroPositionIndex = 0;
    private List<MapPoint> path;

    private boolean isAllFinish = false;

    public DfsDrawer() {
        map = new Map();
        map.generateMap();
        dfs = new DepthFirstSearch(map);

        createGrayArea();
    }

    @Override
    public void draw(Batch batch) {
        drawMapAndDfs(batch);
    }

    public void drawStepByStep(Batch batch) {
        if (isAllFinish) return;

        if (!dfs.isFinished()) {
            handlingPosition = dfs.searchStepByStep();
        } else if (path == null) {
            path = dfs.getPath();
        } else if (heroPositionIndex < path.size() - 1) {
            heroPositionIndex++;
        } else {
            isAllFinish = true;
        }
    }

    private void drawMapAndDfs(Batch batch) {
        int originX = Config.WINDOW_WIDTH / 2 - map.getSize() / 2 * sizeWall;
        int originY = Config.WINDOW_HEIGHT / 2 - map.getSize() / 2 * sizeWall;
        int drawingX = originX;
        int drawingY = originY;

        final MapPoint position = new MapPoint(0, 0);

        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {
                position.update(x, y);
                switch (map.getMapRole(position)) {
                    case WALL:
                        batch.draw(wall, drawingX, drawingY);
                        break;
                    case STAIR_DOWN:
                        batch.draw(stairDown, drawingX, drawingY);
                        break;
                    case STAIR_UP:
                        batch.draw(stairUp, drawingX, drawingY);
                        break;
                    case SPACE:
                        if (position.equals(handlingPosition)) {
                            drawHandlingPosition(batch, drawingX, drawingY);
                        }

                        int cost = dfs.getCost(position);
                        if (cost < Integer.MAX_VALUE) {
                            drawCost(batch, cost, drawingX, drawingY,
                                    (path != null && path.contains(position)));
                        }
                        break;
                }

                drawHero(batch, drawingX, drawingY, position);

                drawingX += sizeWall;
            }
            drawingX = originX;
            drawingY += sizeWall;
        }
    }

    private void drawHandlingPosition(Batch batch, int x, int y) {
        batch.draw(handlingArea, x, y);
    }

    private void drawCost(Batch batch, int cost, int x, int y, boolean isOnPath) {
        bitmapFont.getData().setScale(0.5f);
        bitmapFont.setColor(isOnPath ? Color.RED : Color.WHITE);
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, String.valueOf(cost));
        bitmapFont.draw(
                batch,
                glyphLayout,
                x + (sizeWall - glyphLayout.width) / 2,
                y + sizeWall - (sizeWall - glyphLayout.height) / 2);
    }

    private void drawHero(Batch batch, int x, int y, MapPoint position) {
        MapPoint heroPosition = (path != null ? path.get(heroPositionIndex) : map.getSource());
        if (heroPosition.x == position.x && heroPosition.y == position.y) {
            batch.draw(heroFemale, x, y);
        }
    }

    private void createGrayArea() {
        Pixmap pixmap = new Pixmap(sizeWall, sizeWall, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fillRectangle(0, 0, sizeWall, sizeWall);
        handlingArea = new Texture(pixmap);
        pixmap.dispose();
    }
}
