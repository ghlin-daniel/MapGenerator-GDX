package com.brickgit.mapgeneratorgdx.algorithms;

import com.brickgit.mapgeneratorgdx.utils.MapPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Daniel Lin on 29/04/2018.
 */

public class AStarAlgorithm {

    private Map map;
    private MapPoint position;
    private Queue<MapPoint> queue;
    private boolean isFinished = false;

    private boolean[][] finishedMap;
    private MapPoint[][] gps;
    private double[][] toSourceCosts;
    private double[][] toDestinationCosts;
    private double[][] totalCosts;
    List<MapPoint> directions;

    private List<MapPoint> path;

    public AStarAlgorithm(Map map) {
        this.map = map;
        this.position = this.map.getSource();

        initGpsAndCosts();
        initHeapAndDirections();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public double getCost(MapPoint position) {
        return toSourceCosts[position.x][position.y];
    }

    public List<MapPoint> getPath() {
        return path;
    }

    public MapPoint searchStepByStep() {
        if (!queue.isEmpty()) {
            position = queue.poll();
            finishedMap[position.x][position.y] = true;

            for (MapPoint direction : directions) {
                MapPoint possiblePosition = new MapPoint(position.x + direction.x, position.y + direction.y);
                Map.MapRole role = map.getMapRole(possiblePosition);
                if (role == Map.MapRole.SPACE || role == Map.MapRole.STAIR_DOWN) {
                    if (finishedMap[possiblePosition.x][possiblePosition.y]) continue;
                    double newCost = toSourceCosts[position.x][position.y] + 1;
                    boolean isBetter = false;
                    if (!queue.contains(possiblePosition)) {
                        queue.offer(possiblePosition);
                        isBetter = true;
                    }
                    else if (newCost < toSourceCosts[possiblePosition.x][possiblePosition.y]) {
                        isBetter = true;
                    }
                    if (isBetter) {
                        gps[possiblePosition.x][possiblePosition.y] = position;
                        toSourceCosts[possiblePosition.x][possiblePosition.y] = newCost;
                        totalCosts[possiblePosition.x][possiblePosition.y] =
                                toSourceCosts[possiblePosition.x][possiblePosition.y] + toDestinationCosts[possiblePosition.x][possiblePosition.y];
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
        finishedMap = new boolean[size][size];
        gps = new MapPoint[size][size];
        toSourceCosts = new double[size][size];
        toDestinationCosts = new double[size][size];
        totalCosts = new double[size][size];
        MapPoint destination = map.getDestination();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                finishedMap[x][y] = false;
                gps[x][y] = null;
                toSourceCosts[x][y] = Double.MAX_VALUE;
                toDestinationCosts[x][y] = destination.getDistance(new MapPoint(x, y));
                totalCosts[x][y] = Double.MAX_VALUE;
            }
        }
        toSourceCosts[position.x][position.y] = 0;
        totalCosts[position.x][position.y] =
                toSourceCosts[position.x][position.y] + toDestinationCosts[position.x][position.y];
    }

    private void initHeapAndDirections() {
        this.queue = new PriorityQueue<MapPoint>(new Comparator<MapPoint>() {
            @Override
            public int compare(MapPoint mp1, MapPoint mp2) {
                double cost1 = totalCosts[mp1.x][mp1.y];
                double cost2 = totalCosts[mp2.x][mp2.y];
                return Double.compare(cost1, cost2);
            }
        });
        queue.offer(position);

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
