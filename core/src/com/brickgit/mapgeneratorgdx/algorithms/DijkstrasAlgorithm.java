package com.brickgit.mapgeneratorgdx.algorithms;

import com.brickgit.mapgeneratorgdx.utils.MapPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Daniel Lin on 28/04/2018.
 */

public class DijkstrasAlgorithm {

    private Map map;
    private MapPoint position;
    private Queue<MapPoint> queue;
    private boolean isFinished = false;

    private MapPoint[][] gps;
    private int[][] costs;
    List<MapPoint> directions;

    private List<MapPoint> path;

    public DijkstrasAlgorithm(Map map) {
        this.map = map;
        this.position = this.map.getSource();

        initGpsAndCosts();
        initHeapAndDirections();
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

            for (MapPoint direction : directions) {
                MapPoint possiblePosition = new MapPoint(position.x + direction.x, position.y + direction.y);
                Map.MapRole role = map.getMapRole(possiblePosition);
                if (role == Map.MapRole.SPACE || role == Map.MapRole.STAIR_DOWN) {
                    int newCost = costs[position.x][position.y] + 1;
                    if (newCost < costs[possiblePosition.x][possiblePosition.y]) {
                        gps[possiblePosition.x][possiblePosition.y] = position;
                        costs[possiblePosition.x][possiblePosition.y] = newCost;
                        queue.remove(possiblePosition);
                        queue.offer(possiblePosition);
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
        this.costs[this.position.x][this.position.y] = 0;
    }

    private void initHeapAndDirections() {
        this.queue = new PriorityQueue<MapPoint>(new Comparator<MapPoint>() {
            @Override
            public int compare(MapPoint mp1, MapPoint mp2) {
                int cost1 = costs[mp1.x][mp1.y];
                int cost2 = costs[mp2.x][mp2.y];
                return (cost1 == cost2)
                        ? 0
                        : ((cost1 < cost2) ? -1 : 1);
            }
        });
        int size = map.getSize();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                MapPoint position = new MapPoint(x, y);
                Map.MapRole role = map.getMapRole(position);
                if (role != Map.MapRole.WALL) {
                    queue.offer(position);
                }
            }
        }

        directions = new ArrayList<MapPoint>();
        directions.add(MapPoint.UP);
        directions.add(MapPoint.RIGHT);
        directions.add(MapPoint.DOWN);
        directions.add(MapPoint.LEFT);
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
