package com.brickgit.mapgeneratorgdx.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.brickgit.mapgeneratorgdx.algorithms.Map;
import com.brickgit.mapgeneratorgdx.utils.Config;
import com.brickgit.mapgeneratorgdx.utils.MapPoint;

/**
 * Created by Daniel Lin on 10/04/2018.
 */

public class MapDrawer extends BaseDrawer {

    private Map map;

    public MapDrawer() {
        super();
        map = new Map();
    }

    @Override
    public void draw(Batch batch) {
		drawMap(batch);
    }

    @Override
    public void drawStepByStep(Batch batch) {
        if (!map.isFinished()) {
            map.generateMapStepByStep();
        }
    }

    private void drawMap(Batch batch) {
        int originX = Config.WINDOW_WIDTH / 2 - map.getSize() / 2 * sizeWall;
        int originY = Config.WINDOW_HEIGHT / 2 - map.getSize() / 2 * sizeWall;
        int drawingX = originX;
        int drawingY = originY;

        MapPoint position = new MapPoint(0, 0);

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
                }
                drawingX += sizeWall;
            }
            drawingX = originX;
            drawingY += sizeWall;
        }
    }
}
