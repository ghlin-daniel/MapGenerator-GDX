package com.brickgit.mapgeneratorgdx.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.brickgit.mapgeneratorgdx.algorithms.Map;
import com.brickgit.mapgeneratorgdx.algorithms.Walker;
import com.brickgit.mapgeneratorgdx.utils.Config;
import com.brickgit.mapgeneratorgdx.utils.MapPoint;

/**
 * Created by Daniel Lin on 11/04/2018.
 */

public class WalkerDrawer extends BaseDrawer {

    private Map map;
    private Walker hero;

    public WalkerDrawer() {
        map = new Map();
        map.generateMap();
        hero = new Walker(map);
    }

    @Override
    public void draw(Batch batch) {
        drawMapAndHero(batch);
    }

    @Override
    public void drawStepByStep(Batch batch) {
        if (!hero.isFinished()) {
            hero.walkStepByStep();
        }
    }

    private void drawMapAndHero(Batch batch) {
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

                if (hero.isHere(position)) {
                    batch.draw(heroFemale, drawingX, drawingY);
                }

                drawingX += sizeWall;
            }
            drawingX = originX;
            drawingY += sizeWall;
        }
    }
}
