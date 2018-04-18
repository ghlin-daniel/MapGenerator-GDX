package com.brickgit.mapgeneratorgdx.utils;

/**
 * Created by Daniel Lin on 11/04/2018.
 */

public class MapPoint {

    public final static MapPoint RIGHT = new MapPoint(1, 0);
    public final static MapPoint LEFT = new MapPoint(-1, 0);
    public final static MapPoint UP = new MapPoint(0, 1);
    public final static MapPoint DOWN = new MapPoint(0, -1);

    public int x;
    public int y;

    public MapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapPoint add(MapPoint mp) {
        x += mp.x;
        y += mp.y;
        return this;
    }

    public MapPoint sub(MapPoint mp) {
        x -= mp.x;
        y -= mp.y;
        return this;
    }

    public MapPoint update(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public boolean isSameOrReverse(MapPoint mp) {
        return Math.abs(this.x) == Math.abs(mp.x) &&
                Math.abs(this.y) == Math.abs(mp.y);
    }

    public double getDistance(MapPoint mp) {
        int distanceX = x - mp.x;
        int distanceY = y - mp.y;
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public boolean equals(Object object) {
        if (!(object instanceof MapPoint)) {
            return super.equals(object);
        }
        else {
            MapPoint point = (MapPoint) object;
            return this.x == point.x && this.y == point.y;
        }
    }

    public String toString() {
        return this.getClass().getName() + " (" + this.x + ", " + this.y + ")";
    }
}
