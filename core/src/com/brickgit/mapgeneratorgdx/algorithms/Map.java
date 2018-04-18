package com.brickgit.mapgeneratorgdx.algorithms;

import com.brickgit.mapgeneratorgdx.utils.MapPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel Lin on 06/04/2018.
 */

public class Map {

    public enum MapRole {
        WALL, SPACE, STAIR_UP, STAIR_DOWN
    }

    private int size = 32;
    private int numTunnels = 70;
    private int maxLenTunnel = 20;

    private MapRole[][] map;
    private MapPoint positionStairUp;
    private MapPoint positionStairDown;

    private boolean isFinished = false;
    private Random random = new Random();
    private MapPoint lastDirection = new MapPoint(0, 0);
    private MapPoint position;

    public Map() {
        initMap();
        position = new MapPoint(random.nextInt(size - 2) + 1, random.nextInt(size - 2) + 1);
        map[position.x][position.y] = MapRole.SPACE;
    }

    public Map(int size, int numTunnels, int maxLenTunnel) {
        this();

        this.size = size;
        this.numTunnels = numTunnels;
        this.maxLenTunnel = maxLenTunnel;
    }

    public int getSize() {
        return size;
    }

    public MapPoint getSource() {
        return positionStairUp;
    }

    public MapPoint getDestination() {
        return positionStairDown;
    }

    public MapRole getMapRole(MapPoint position) {
        return map[position.x][position.y];
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void generateMap() {
        while (!isFinished) {
            generateMapStepByStep();
        }
    }

    public void generateMapStepByStep() {
        List<MapPoint> directions = new ArrayList<MapPoint>();
        directions.add(MapPoint.RIGHT);
        directions.add(MapPoint.LEFT);
        directions.add(MapPoint.UP);
        directions.add(MapPoint.DOWN);

        while (!directions.isEmpty()) {
            MapPoint direction = directions.remove(random.nextInt(directions.size()));
            if (direction.isSameOrReverse(lastDirection)) continue;

            boolean isDrawn = false;
            int len = random.nextInt(maxLenTunnel) + 1;
            while (len > 0) {
                position.add(direction);
                if (position.x == 0 || position.x == size - 1 ||
                        position.y == 0 || position.y == size - 1) {
                    position.sub(direction);
                    break;
                }
                map[position.x][position.y] = MapRole.SPACE;
                isDrawn = true;
                len--;
            }

            if (isDrawn) {
                lastDirection = direction;
                numTunnels--;
                if (numTunnels == 0) {
                    generateStairs();
                    isFinished = true;
                }
                return;
            }
        }

        generateStairs();
        isFinished = true;
    }

    private void generateStairs() {
        double distance;
        do {
            do {
                positionStairUp = new MapPoint(random.nextInt(size - 2) + 1, random.nextInt(size - 2) + 1);
            } while (map[positionStairUp.x][positionStairUp.y] != MapRole.SPACE);

            do {
                positionStairDown = new MapPoint(random.nextInt(size - 2) + 1, random.nextInt(size - 2) + 1);
            } while (map[positionStairDown.x][positionStairDown.y] != MapRole.SPACE);

            distance = positionStairUp.getDistance(positionStairDown);
        } while (distance < 10);
        map[positionStairUp.x][positionStairUp.y] = MapRole.STAIR_UP;
        map[positionStairDown.x][positionStairDown.y] = MapRole.STAIR_DOWN;
    }

    private void initMap() {
        map = new MapRole[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                map[x][y] = MapRole.WALL;
            }
        }
    }
}
