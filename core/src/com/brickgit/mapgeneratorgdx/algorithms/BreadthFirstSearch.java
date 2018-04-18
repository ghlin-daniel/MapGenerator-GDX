package com.brickgit.mapgeneratorgdx.algorithms;

import com.brickgit.mapgeneratorgdx.utils.MapPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Daniel Lin on 15/04/2018.
 */

public class BreadthFirstSearch {

    private Map map;
    private MapPoint position;
    private Queue<MapPoint> queue;
    private boolean isFinished = false;

    private MapPoint[][] gps;
    private int[][] costs;

    private List<MapPoint> path;

    public BreadthFirstSearch(Map map) {
        this.map = map;
        this.position = this.map.getSource();

        initGpsAndCosts();

        this.queue = new LinkedList<MapPoint>();
        this.queue.offer(this.position);
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
        if (!queue.isEmpty()) {
            position = queue.poll();

            List<MapPoint> directions = new ArrayList<MapPoint>();
            directions.add(MapPoint.UP);
            directions.add(MapPoint.RIGHT);
            directions.add(MapPoint.DOWN);
            directions.add(MapPoint.LEFT);

            while (!directions.isEmpty()) {
                MapPoint direction = directions.remove(0);
                MapPoint possiblePosition = new MapPoint(position.x + direction.x, position.y + direction.y);
                Map.MapRole role = map.getMapRole(possiblePosition);
                if (role == Map.MapRole.SPACE || role == Map.MapRole.STAIR_DOWN) {
                    if (costs[possiblePosition.x][possiblePosition.y] == Integer.MAX_VALUE) {
                        queue.offer(possiblePosition);
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
