package com.brickgit.mapgeneratorgdx.algorithms;

import com.brickgit.mapgeneratorgdx.utils.MapPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Daniel Lin on 14/04/2018.
 */

public class DepthFirstSearch {

    private Map map;
    private MapPoint position;
    private Stack<MapPoint> stack;
    private boolean isFinished = false;

    private MapPoint[][] gps;
    private int[][] costs;

    private List<MapPoint> path;

    public DepthFirstSearch(Map map) {
        this.map = map;
        this.position = this.map.getSource();

        initGpsAndCosts();

        this.stack = new Stack<MapPoint>();
        this.stack.push(this.position);
        this.costs[this.position.x][this.position.y] = 0;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getCost(MapPoint position) {
        return costs[position.x][position.y];
    }

    public List<MapPoint> getPath() {
        return path;
    }

    public MapPoint searchStepByStep() {
        if (!stack.isEmpty()) {
            position = stack.pop();

            List<MapPoint> directions = new ArrayList<MapPoint>();
            directions.add(MapPoint.LEFT);
            directions.add(MapPoint.DOWN);
            directions.add(MapPoint.RIGHT);
            directions.add(MapPoint.UP);

            while (!directions.isEmpty()) {
                MapPoint direction = directions.remove(0);
                MapPoint possiblePosition = new MapPoint(position.x + direction.x, position.y + direction.y);
                Map.MapRole role = map.getMapRole(possiblePosition);
                if (role == Map.MapRole.SPACE || role == Map.MapRole.STAIR_DOWN) {
                    if (costs[possiblePosition.x][possiblePosition.y] == Integer.MAX_VALUE) {
                        stack.push(possiblePosition);
                        gps[possiblePosition.x][possiblePosition.y] = position;
                        costs[possiblePosition.x][possiblePosition.y] = costs[position.x][position.y] + 1;
                    }
                }
            }
        }
        else {
            buildPath();
            position = null;
            isFinished = true;
        }

        return position;
    }

    private void initGpsAndCosts() {
        int size = map.getSize();
        gps = new MapPoint[size][size];
        costs = new int[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                gps[x][y] = null;
                costs[x][y] = Integer.MAX_VALUE;
            }
        }
    }

    private void buildPath() {
        path = new LinkedList<MapPoint>();
        MapPoint currentPosition = map.getDestination();
        path.add(0, currentPosition);
        while (!currentPosition.equals(map.getSource())) {
            MapPoint previousPosition = gps[currentPosition.x][currentPosition.y];
            path.add(0, previousPosition);
            currentPosition = previousPosition;
        }
    }
}
