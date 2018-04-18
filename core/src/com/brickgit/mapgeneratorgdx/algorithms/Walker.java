package com.brickgit.mapgeneratorgdx.algorithms;

import com.brickgit.mapgeneratorgdx.utils.MapPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Daniel Lin on 11/04/2018.
 */

public class Walker {

    private Map map;
    private MapPoint position;
    private Stack<MapPoint> stack;
    private boolean isFinished = false;

    private Boolean[][] gps;

    public Walker(Map map) {
        this.map = map;
        this.position = this.map.getSource();

        initGps();

        this.stack = new Stack<MapPoint>();
        this.stack.push(this.position);
        this.gps[this.position.x][this.position.y] = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isHere(MapPoint position) {
        return this.position.x == position.x && this.position.y == position.y;
    }

    public void walkStepByStep() {
        if (!stack.isEmpty()) {
            position = stack.pop();

            if (position.equals(map.getDestination())) {
                stack.clear();
                isFinished = true;
                return;
            }

            List<MapPoint> directions = new ArrayList<MapPoint>();
            directions.add(MapPoint.LEFT);
            directions.add(MapPoint.DOWN);
            directions.add(MapPoint.RIGHT);
            directions.add(MapPoint.UP);

            while (!directions.isEmpty()) {
                MapPoint direction = directions.remove(0);
                MapPoint possiblePosition = new MapPoint(position.x + direction.x, position.y + direction.y);
                Map.MapRole role = map.getMapRole(possiblePosition);
                if (!gps[possiblePosition.x][possiblePosition.y] &&
                        (role == Map.MapRole.SPACE || role == Map.MapRole.STAIR_DOWN)) {
                    stack.push(position);
                    stack.push(possiblePosition);
                    gps[possiblePosition.x][possiblePosition.y] = true;
                }
            }
        }
    }

    private void initGps() {
        int size = map.getSize();
        gps = new Boolean[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                gps[x][y] = false;
            }
        }
    }
}
